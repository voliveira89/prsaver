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

    //TODO check if user exists
    fun createPersonalRecord(serverRequest: ServerRequest) =
        serverRequest.body(BodyExtractors.toMono(PersonalRecord::class.java))
            .flatMap {
                personalRecordRepository.save(it)
                    .then(created(URI.create("/pr/" + it.id)).build())
            }

    fun deletePersonalRecord(serverRequest: ServerRequest) =
        personalRecordRepository.findById(serverRequest.pathVariable("id"))
            .flatMap {
                personalRecordRepository.delete(it)
                    .then(ok().build())
            }
            .switchIfEmpty(notFound().build())

    fun updatePersonalRecord(serverRequest: ServerRequest) =
        serverRequest.body(BodyExtractors.toMono(PersonalRecord::class.java))
            .flatMap { pr ->
                personalRecordRepository.findById(pr.id)
                    .flatMap {
                        personalRecordRepository.save(pr)
                            .then(ok().body(Mono.just(pr), PersonalRecord::class.java))
                    }
                    .switchIfEmpty(notFound().build())
            }
}
