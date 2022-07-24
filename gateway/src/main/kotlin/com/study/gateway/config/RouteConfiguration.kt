package com.study.gateway.config

import com.study.gateway.filter.AuthorizationHeaderFilter
import com.study.gateway.property.ServiceProperties
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean

class RouteConfiguration(
    private val serviceProperties: ServiceProperties,
    private val authorizationHeaderFilterFactory: AuthorizationHeaderFilter
) {
    companion object {
        private const val VIEW_SERVICE_ID = "view-service"
        private const val USER_SERVICE_ID = "user-service"
        private const val ORDER_SERVICE_ID = "order-service"
    }

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator = builder.routes {
        route(id = "${USER_SERVICE_ID}-auth-filter") {
            path("/oauth2/**", "/api/v1/auth/**")
            filters {
                rewritePath("/api/v1/(?<path>.*)", "/\$\\{path}")
            }
            uri(serviceProperties.getUri(USER_SERVICE_ID))
        }
        route(id = "${USER_SERVICE_ID}-global-filter") {
            path("/api/v1/users/**")
            filters {
                removeRequestHeader("Cookie")
                rewritePath("/api/v1/(?<path>.*)", "/\$\\{path}")
                filter(authorizationHeaderFilterFactory.apply(AuthorizationHeaderFilter.Config()))
            }
            uri(serviceProperties.getUri(USER_SERVICE_ID))
        }
    }
}