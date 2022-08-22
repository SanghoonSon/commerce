package com.study.authservice.global.security.oauth2

import com.study.authservice.global.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.Companion.OAUTH2_FAIL_REDIRECT_URI_PARAM_COOKIE_NAME
import com.study.authservice.global.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.Companion.OAUTH2_SUCCESS_REDIRECT_URI_PARAM_COOKIE_NAME
import com.study.authservice.global.util.CookieUtils
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2AuthenticationFailureHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository
): SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        var targetUrl: String = CookieUtils.getCookie(request, OAUTH2_FAIL_REDIRECT_URI_PARAM_COOKIE_NAME)
            .map{ it.value }
            .orElse("/")

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("error", exception.localizedMessage)
            .build().toUriString()

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}