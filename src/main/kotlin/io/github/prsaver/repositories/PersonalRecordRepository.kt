package io.github.prsaver.repositories

import io.github.prsaver.model.PersonalRecord
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface PersonalRecordRepository : ReactiveMongoRepository<PersonalRecord, String> {
    fun findByUserId(userId: String): Flux<PersonalRecord>
}