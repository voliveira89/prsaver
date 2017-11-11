package io.github.prsaver.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document
data class PersonalRecord(@Id val id: String = UUID.randomUUID().toString(),
                          val userId: String,
                          val raceName: String,
                          val location: String,
                          val distance: String,
                          val date: LocalDate,
                          val filePath: String)