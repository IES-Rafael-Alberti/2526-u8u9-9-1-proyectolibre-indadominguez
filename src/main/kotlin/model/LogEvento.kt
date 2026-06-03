package model

import java.time.LocalDateTime
import java.util.*

data class LogEvento(
    val id: String = UUID.randomUUID().toString(),
    val tipo: String,
    val mensaje: String,
    val fecha: LocalDateTime = LocalDateTime.now()
)
