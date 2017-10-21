package io.github.prsaver.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document
data class User(@Id val id: String = UUID.randomUUID().toString(),
                val name: String,
                val birthDate: LocalDate)