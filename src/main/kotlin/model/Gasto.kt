package model

import java.time.LocalDate

data class Gasto(
    val id: Long = 0,
    val fecha: LocalDate,
    val cantidad: Double,
    val descripcion: String = "",
    val categoriaId: Long? = null
)
