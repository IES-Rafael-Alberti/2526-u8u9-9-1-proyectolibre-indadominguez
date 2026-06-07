package validator

import exception.ValidacionException
import model.Gasto
import java.time.LocalDate

object GastoValidator {

    fun validar(gasto: Gasto) {
        val errores = mutableListOf<String>()

        if (gasto.descripcion.isBlank()) {
            errores.add("La descripción del gasto no puede estar vacía")
        } else if (gasto.descripcion.length !in 1..200) {
            errores.add("La descripción debe tener entre 1 y 200 caracteres")
        }

        if (gasto.monto <= 0) {
            errores.add("El monto debe ser positivo")
        }

        if (gasto.monto.toString().substringAfter(".").length > 2) {
            errores.add("El monto debe tener como máximo 2 decimales")
        }

        if (gasto.fecha.isAfter(LocalDate.now())) {
            errores.add("La fecha no puede ser futura")
        }

        if (gasto.categoriaId <= 0) {
            errores.add("Debe especificar una categoría válida")
        }

        if (errores.isNotEmpty()) {
            throw ValidacionException(errores.joinToString("; "))
        }
    }
}