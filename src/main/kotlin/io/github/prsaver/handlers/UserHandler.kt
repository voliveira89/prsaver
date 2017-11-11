package io.github.prsaver.handlers

import io.github.prsaver.model.User
import io.github.prsaver.repositories.PersonalRecordRepository
import io.github.prsaver.repositories.UserRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.net.URI
import java.time.LocalDateTime

@Component
class UserHandler(val userRepository: UserRepository, val personalRecordRepository: PersonalRecordRepository) {

    fun getUser(serverRequest: ServerRequest) =
        userRepository.findById(serverRequest.pathVariable("id"))
            .flatMap { ok().body(Mono.just(it), User::class.java) }
            .switchIfEmpty(notFound().build())

    fun getAllPersonalRecords(serverRequest: ServerRequest) =
        ok().body(personalRecordRepository.findByUserId(serverRequest.pathVariable("id")))

    //TODO check if user is already signed up
    fun createUser(serverRequest: ServerRequest) =
        serverRequest.body(BodyExtractors.toMono(User::class.java))
            .flatMap {
                it.signUpDate = LocalDateTime.now()
                userRepository.save(it)
            }
            .flatMap { created(URI.create("/users/" + it.id)).build() }

    fun deleteUser(serverRequest: ServerRequest) =
        userRepository.findById(serverRequest.pathVariable("id"))
            .flatMap {
                userRepository.delete(it)
                    .then(ok().build())
            }
            .switchIfEmpty(notFound().build())

    fun updateUser(serverRequest: ServerRequest) =
        serverRequest.body(BodyExtractors.toMono(User::class.java))
            .flatMap { user ->
                userRepository.findById(user.id)
                    .flatMap {
                        userRepository.save(user)
                            .then(ok().body(Mono.just(user), User::class.java))
                    }
                    .switchIfEmpty(notFound().build())
            }
}


