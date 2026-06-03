package validator

import exception.ValidacionException
import model.Gasto
import java.time.LocalDate

object GastoValidator {

    private val DESCRIPCION_REGEX = Regex("^.{1,200}$")
    private val MONTO_REGEX = Regex("^\\d+(\\.\\d{1,2})?$")

    fun validar(gasto: Gasto) {
        val errores = mutableListOf<String>()

        if (gasto.descripcion.isBlank()) {
            errores.add("La descripción del gasto no puede estar vacía")
        } else if (!DESCRIPCION_REGEX.matches(gasto.descripcion)) {
            errores.add("La descripción debe tener entre 1 y 200 caracteres")
        }

        if (gasto.monto <= 0) {
            errores.add("El monto debe ser positivo")
        } else if (!MONTO_REGEX.matches(gasto.monto.toString())) {
            errores.add("El monto debe tener como máximo 2 decimales")
        }

        if (gasto.fecha == null) {
            errores.add("La fecha no puede ser nula")
        } else if (gasto.fecha.isAfter(LocalDate.now())) {
            errores.add("La fecha no puede ser futura")
        }

        if (gasto.categoriaId <= 0) {
            errores.add("Debe especificar una categoría válida")
        }

        if (errores.isNotEmpty()) {
            throw ValidacionException("Error de validación de gasto: ${errores.joinToString("; ")}")
        }
    }
}
