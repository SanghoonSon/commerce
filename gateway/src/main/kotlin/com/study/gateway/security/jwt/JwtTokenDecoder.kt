package com.study.gateway.security.jwt

import com.study.gateway.property.TokenProperty
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component

@Component
class JwtTokenDecoder(private val tokenProperty: TokenProperty) {

    fun isValidToken(accessToken: String): Boolean {
        val subject: String? = try {
            Jwts.parser()
                .setSigningKey(tokenProperty.secret)
                .parseClaimsJws(accessToken)
                .body
                .subject
        } catch (e: Exception) {
            return false
        }

        return !(subject == null || subject.isEmpty())
    }
}