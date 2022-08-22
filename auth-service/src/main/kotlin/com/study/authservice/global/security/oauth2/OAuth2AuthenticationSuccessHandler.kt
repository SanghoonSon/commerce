package com.study.authservice.global.security.oauth2

import com.study.authservice.entity.cache.RefreshToken
import com.study.authservice.global.exception.BadRequestException
import com.study.authservice.global.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.Companion.OAUTH2_REGISTRATION_ID_COOKIE_NAME
import com.study.authservice.global.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.Companion.OAUTH2_SUCCESS_REDIRECT_URI_PARAM_COOKIE_NAME
import com.study.authservice.global.util.CookieUtils
import com.study.authservice.property.AppProperties
import com.study.authservice.repository.cache.RefreshTokenRedisRepository
import com.study.authservice.service.cache.JwtTokenService
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationSuccessHandler(
    private val appProperties: AppProperties,
    private val jwtTokenService: JwtTokenService,
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val refreshTokenRedisRepository: RefreshTokenRedisRepository
): SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val targetUrl = determineTargetUrl(request, response, authentication)

        if(response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl");
            return;
        }

        clearAuthenticationAttributes(request, response);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    override fun determineTargetUrl(request: HttpServletRequest,
                                    response: HttpServletResponse,
                                    authentication: Authentication): String {
        val redirectUri = CookieUtils.getCookie(request, OAUTH2_SUCCESS_REDIRECT_URI_PARAM_COOKIE_NAME).map { it.value }
        val registrationId = CookieUtils.getCookie(request, OAUTH2_REGISTRATION_ID_COOKIE_NAME).map { it.value }
        if(redirectUri.isPresent && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        val targetUrl = redirectUri.orElse(defaultTargetUrl)
        val tokenDto = jwtTokenService.createJwtTokens(authentication)
        var refreshToken = refreshTokenRedisRepository.findByUserId(tokenDto.userId)

        if(refreshToken != null) {
            refreshToken.refreshToken = tokenDto.refreshToken
        } else {
            refreshToken = RefreshToken(tokenDto.userId, tokenDto.refreshToken)
            refreshTokenRedisRepository.save(refreshToken)
        }

        CookieUtils.addCookie(response,
            "refresh-token",
            tokenDto.refreshToken,
            tokenDto.refreshTokenValidityTime.toInt())

        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("oauthType", registrationId )
            .queryParam("token", tokenDto.accessToken)
            .build()
            .toString()
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    private fun isAuthorizedRedirectUri(redirectUri: String): Boolean {
        val clientRedirectUri = URI.create(redirectUri)
        return appProperties.oAuth2.authorizedRedirectUris
            .stream()
            .anyMatch { authorizedRedirectUri ->
                // Only validate host and port. Let the clients use different paths if they want to
                val authorizedURI = URI.create(authorizedRedirectUri)
                return@anyMatch (authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true)
                        && authorizedURI.port == clientRedirectUri.port)
            }
    }
}