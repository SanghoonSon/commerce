package com.study.authservice.global.security.oauth2

import com.nimbusds.oauth2.sdk.util.StringUtils
import com.study.authservice.global.util.CookieUtils
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HttpCookieOAuth2AuthorizationRequestRepository(): AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    companion object {
        const val OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request"
        const val OAUTH2_REGISTRATION_ID_COOKIE_NAME = "oauth2_registration_id"
        const val OAUTH2_SUCCESS_REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"
        const val OAUTH2_FAIL_REDIRECT_URI_PARAM_COOKIE_NAME = "error_uri"
        private const val cookieExpireSeconds = 180
    }

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            .map { cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest::class.java) }
            .orElse(null)
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        if(authorizationRequest == null) {
            CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtils.deleteCookie(request, response, OAUTH2_SUCCESS_REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }

        CookieUtils.addCookie(
            response,
            OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtils.serialize(authorizationRequest),
            cookieExpireSeconds
        )

        CookieUtils.addCookie(
            response,
            OAUTH2_REGISTRATION_ID_COOKIE_NAME,
            authorizationRequest.attributes["registration_id"] as String,
            cookieExpireSeconds
        )

        addCookieIfNotBlankParameter(request, response, OAUTH2_SUCCESS_REDIRECT_URI_PARAM_COOKIE_NAME)
        addCookieIfNotBlankParameter(request, response, OAUTH2_FAIL_REDIRECT_URI_PARAM_COOKIE_NAME)
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return this.loadAuthorizationRequest(request)
    }

    fun removeAuthorizationRequestCookies(request: HttpServletRequest, response: HttpServletResponse) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        CookieUtils.deleteCookie(request, response, OAUTH2_REGISTRATION_ID_COOKIE_NAME)
        CookieUtils.deleteCookie(request, response, OAUTH2_SUCCESS_REDIRECT_URI_PARAM_COOKIE_NAME)
        CookieUtils.deleteCookie(request, response, OAUTH2_FAIL_REDIRECT_URI_PARAM_COOKIE_NAME)
    }

    private fun addCookieIfNotBlankParameter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        parameterName: String,
    ) {
        val parameter = request.getParameter(parameterName)
        if (StringUtils.isNotBlank(parameter)) {
            CookieUtils.addCookie(
                response,
                parameterName,
                parameter,
                cookieExpireSeconds
            )
        }
    }
}