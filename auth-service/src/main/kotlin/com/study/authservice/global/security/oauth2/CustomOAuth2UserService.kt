package com.study.authservice.global.security.oauth2

import com.study.authservice.entity.vo.AuthProvider
import com.study.authservice.entity.AuthProviderType
import com.study.authservice.entity.OAuthUser
import com.study.authservice.global.exception.OAuth2AuthenticationProcessingException
import com.study.authservice.global.security.UserPrincipal
import com.study.authservice.global.security.oauth2.user.OAuth2UserInfo
import com.study.authservice.global.security.oauth2.user.OAuth2UserInfoFactory
import com.study.authservice.repository.OAuthUserRepository
import mu.KotlinLogging
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.util.*
import java.util.function.Supplier

@Service
class CustomOAuth2UserService(
    private val oAuthUserRepository: OAuthUserRepository
): DefaultOAuth2UserService() {

    private val logger = KotlinLogging.logger {}

    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(oAuth2UserRequest)

        return try {
            processOAuth2User(oAuth2UserRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val registrationId = oAuth2UserRequest.clientRegistration.registrationId
        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
            registrationId,
            oAuth2User.attributes
        )

        val email = oAuth2UserInfo.getEmail()
        if (!StringUtils.hasText(email)) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }

        val authProvider = AuthProvider(AuthProviderType.valueOf(registrationId.uppercase()), oAuth2UserInfo.getId())
        logger.info{ "Process OAuth User load - email : $email, Auth Provider : $authProvider" }
        val oAuthUser: OAuthUser = oAuthUserRepository.findByEmailAndAuthProvider(email, authProvider)
            ?: registerNewOAuthUser(oAuth2UserRequest, oAuth2UserInfo, authProvider)

        /*if(oAuthUserOptional.isPresent) {
            oAuthUser = oAuthUserOptional.get()
            val authProviderType = AuthProviderType.valueOf(registrationId.uppercase())
            if(!oAuthUser.isSameAuthProvider(authProviderType)) {
                val oAuthUserProviderType = oAuthUser.authProvider.authProviderType
                throw OAuth2AuthenticationProcessingException(
                    "Looks like you're signed up with $oAuthUserProviderType account. Please use your $oAuthUserProviderType account to login."
                )
            }

            updateExistingOAuthUser(oAuthUser, oAuth2UserInfo)
        } else {
            oAuthUser = registerNewOAuthUser(oAuth2UserRequest, oAuth2UserInfo)
        }*/


        return OAuthUserPrincipal.create(oAuthUser, oAuth2User.attributes)
    }

    @Transactional
    fun updateExistingOAuthUser(existingUser: OAuthUser, oAuth2UserInfo: OAuth2UserInfo) {
        existingUser.email = oAuth2UserInfo.getName()
        existingUser.imageUrl = oAuth2UserInfo.getImageUrl()
        oAuthUserRepository.save(existingUser)
    }

    @Transactional
    fun registerNewOAuthUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo, oAuthProvider: AuthProvider): OAuthUser {
        val oAuthUser = OAuthUser(
            email = oAuth2UserInfo.getEmail(),
            authProvider = oAuthProvider,
            imageUrl = oAuth2UserInfo.getImageUrl()
        )

        return oAuthUserRepository.save(oAuthUser)
    }
}