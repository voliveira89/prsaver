package io.github.prsaver.config

import io.github.prsaver.handlers.PersonalRecordHandler
import io.github.prsaver.handlers.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class PrsaverRoutes(val userHandler: UserHandler,
                    val personalRecordHandler: PersonalRecordHandler) {

    @Bean
    fun apiRouter() = router {
        (accept(MediaType.APPLICATION_JSON) and "/api").nest {
            "/users".nest {
                GET("/{id}", userHandler::getUser)
                GET("/{id}/pr", userHandler::getAllPersonalRecords)
                POST("/", userHandler::createUser)
                DELETE("/{id}", userHandler::deleteUser)
                PUT("/", userHandler::updateUser)
            }
            "/pr".nest {
                GET("/{id}", personalRecordHandler::getPersonalRecord)
                POST("/", personalRecordHandler::createPersonalRecord)
                DELETE("/{id}", personalRecordHandler::deletePersonalRecord)
                PUT("/", personalRecordHandler::updatePersonalRecord)
            }
        }
    }
}
