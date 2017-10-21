package io.github.prsaver.config

import io.github.prsaver.handlers.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class PrsaverRoutes {

    @Bean
    fun apiRouter(userHandler: UserHandler) =
            router {
                (accept(MediaType.APPLICATION_JSON) and "/api").nest {
                    "/users".nest {
                        GET("/{id}", userHandler::getUser)
                        POST("/", userHandler::createUser)
                    }
                }
            }
}
