package com.study.commerce.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.service.ApiInfo
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.spi.DocumentationType
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.PathSelectors
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.HashSet

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun userSwaggerApi(): Docket {
        return createDocket("users", "com.study.commerce.user", "/**")
    }

    @Bean
    fun productSwaggerApi(): Docket {
        return createDocket("products", "com.study.commerce.product", "/**")
    }

    @Bean
    fun orderSwaggerApi(): Docket {
        return createDocket("orders", "com.study.commerce.order", "/api/**")
    }

    private fun swaggerInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Commerce API")
            .description("Commerce API Docs")
            .version("1.0")
            .build()
    }

    private fun createDocket(groupName: String, basePackage: String, urlPattern: String): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .consumes(consumeContentTypes)
            .produces(produceContentTypes)
            .groupName(groupName)
            .apiInfo(swaggerInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.ant(urlPattern))
            .build()
            .useDefaultResponseMessages(false)
    }

    private val consumeContentTypes: Set<String>
        private get() {
            val consumes: MutableSet<String> = HashSet()
            consumes.add("application/json;charset=UTF-8")
            consumes.add("application/x-www-form-urlencoded")
            return consumes
        }
    private val produceContentTypes: Set<String>
        private get() {
            val produces: MutableSet<String> = HashSet()
            produces.add("application/json;charset=UTF-8")
            return produces
        }
}