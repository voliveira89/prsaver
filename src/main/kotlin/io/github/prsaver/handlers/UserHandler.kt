package io.github.prsaver.handlers

import io.github.prsaver.model.User
import io.github.prsaver.repositories.UserRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI

@Component
class UserHandler(val userRepository: UserRepository) {

    fun getUser(serverRequest: ServerRequest) =
            userRepository.findById(serverRequest.pathVariable("id"))
                    .flatMap { ServerResponse.ok().body(Mono.just(it), User::class.java) }
                    .switchIfEmpty(ServerResponse.notFound().build())

    fun createUser(serverRequest: ServerRequest) =
            serverRequest.body(BodyExtractors.toMono(User::class.java))
                    .flatMap { userRepository.save(it) }
                    .flatMap { ServerResponse.created(URI.create("/users/" + it.id)).build() }
}
