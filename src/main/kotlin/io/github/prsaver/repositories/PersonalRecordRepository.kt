package io.github.prsaver.repositories

import io.github.prsaver.model.PersonalRecord
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonalRecordRepository : ReactiveMongoRepository<PersonalRecord, String>