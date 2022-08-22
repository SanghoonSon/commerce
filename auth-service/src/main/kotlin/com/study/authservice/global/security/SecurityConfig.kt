package com.study.authservice.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.authservice.global.security.filter.JwtAuthenticationFilter
import com.study.authservice.global.security.oauth2.CustomOAuth2UserService
import com.study.authservice.global.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository
import com.study.authservice.global.security.oauth2.OAuth2AuthenticationFailureHandler
import com.study.authservice.global.security.oauth2.OAuth2AuthenticationSuccessHandler
import com.study.authservice.service.cache.JwtTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*

@Configuration
@EnableWebSecurity
// https://github.com/callicoder/spring-boot-react-oauth2-social-login-demo/blob/1b77669e0ca326b1c556a83dc0c3bae233653788/spring-social/src/main/java/com/example/springsocial/security/oauth2/user/GithubOAuth2UserInfo.java
class SecurityConfig(
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler,
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val objectMapper: ObjectMapper,
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenService: JwtTokenService
) {


    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun filterChain(http: HttpSecurity, authenticationConfiguration: AuthenticationConfiguration): SecurityFilterChain {
        val jwtAuthenticationFilter =
            JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), objectMapper, jwtTokenService)

        http
            .cors()
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .formLogin()
                .disable()
            .httpBasic()
                .disable()
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers("/",
                    "/error",
                    "/favicon.ico",
                    "/**/*.png",
                    "/**/*.gif",
                    "/**/*.svg",
                    "/**/*.jpg",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js")
                    .permitAll()
                .antMatchers("/api/v1/auth/**", "/oauth2/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                /*.and()
            .oauth2Login()
                .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                    .and()
                .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                */
                 */

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration().apply {
            allowedOriginPatterns = Collections.singletonList(CorsConfiguration.ALL)
            allowedMethods = listOf("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
            allowCredentials = true
            //the below three lines will add the relevant CORS response headers
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", corsConfiguration)
        }
    }

}