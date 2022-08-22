package com.study.authservice.global.security.jwt

import com.study.authservice.global.security.UserPrincipal
import com.study.authservice.property.AppProperties
import io.jsonwebtoken.*
import mu.KotlinLogging
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*


@Component
class TokenProvider(
    private val appProperties: AppProperties
) {
    private val logger = KotlinLogging.logger {}

    fun createToken(authentication: Authentication): String {
        val principal = authentication.principal as UserPrincipal
        val expiryDate = createAccessTokenExpiredTime()

        return Jwts.builder()
            .setSubject(principal.email)
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, appProperties.auth.tokenSecret)
            .compact()
    }

    fun createRefreshToken(userEmail: String): String {
        val expiryDate = Date(Date().time + appProperties.auth.refreshTokenExpirationMsec)
        return Jwts.builder()
            .setSubject(userEmail)
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, appProperties.auth.tokenSecret)
            .compact()
    }

    fun getExpiredTime(token: String): Date {
        val claims = parseClaims(token)
        return claims.body.expiration
    }

    fun getUserIdFromToken(token: String): String {
        val claims = parseClaims(token)
        return claims.body.subject
    }

    fun createAccessTokenExpiredTime(): Date {
        return Date(Date().time + appProperties.auth.tokenExpirationMsec)
    }

    fun getAccessTokenExpirationSeconds(): Long {
        return appProperties.auth.tokenExpirationMsec / 1000
    }

    fun getRefreshTokenExpirationSeconds(): Long {
        return appProperties.auth.refreshTokenExpirationMsec / 1000
    }

    fun validateToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(authToken)
            return true
        } catch (ex: SignatureException) {
            logger.error{ "Invalid JWT signature" }
        } catch (ex: MalformedJwtException) {
            logger.error{ "Invalid JWT token" }
        } catch (ex: ExpiredJwtException) {
            logger.error{ "Expired JWT token" }
        } catch (ex: UnsupportedJwtException) {
            logger.error{ "Unsupported JWT token"}
        } catch (ex: IllegalArgumentException) {
            logger.error{ "JWT claims string is empty." }
        }
        return false
    }

    private fun parseClaims(token: String) = Jwts.parser()
        .setSigningKey(appProperties.auth.tokenSecret)
        .parseClaimsJws(token)


}
