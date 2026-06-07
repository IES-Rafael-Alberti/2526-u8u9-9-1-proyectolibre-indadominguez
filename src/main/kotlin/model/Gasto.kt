package model

import java.time.LocalDate

data class Gasto(
    val id: Long = 0,
    val descripcion: String,
    val monto: Double,
    val fecha: LocalDate,
    val categoriaId: Long
)
