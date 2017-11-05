package io.github.prsaver.handlers

import io.github.prsaver.model.PersonalRecord
import io.github.prsaver.repositories.PersonalRecordRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import java.net.URI

@Component
class PersonalRecordHandler(val personalRecordRepository: PersonalRecordRepository) {

    fun getPersonalRecord(serverRequest: ServerRequest) =
        personalRecordRepository.findById(serverRequest.pathVariable("id"))
            .flatMap { ok().body(Mono.just(it), PersonalRecord::class.java) }
            .switchIfEmpty(notFound().build())

    fun createPersonalRecord(serverRequest: ServerRequest) =
        serverRequest.body(BodyExtractors.toMono(PersonalRecord::class.java))
            .flatMap { personalRecordRepository.save(it) }
            .flatMap { created(URI.create("/pr/" + it.id)).build() }

    fun deletePersonalRecord(serverRequest: ServerRequest) =
        personalRecordRepository.findById(serverRequest.pathVariable("id"))
            .flatMap { personalRecordRepository.delete(it).then(ok().build()) }
            .switchIfEmpty(notFound().build())

    //TODO update personal record
}
