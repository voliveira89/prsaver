package io.github.prsaver.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Document
data class User(@Id val id: String = UUID.randomUUID().toString(),
                val name: String,
                val email: String,
                val birthDate: LocalDate,
                var signUpDate: LocalDateTime?)