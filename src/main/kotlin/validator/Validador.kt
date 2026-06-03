package validator

import exception.ValidationException
import java.time.LocalDate

object Validador {

    fun validarCantidad(valor: String): Double {
        val cantidad = valor.replace(",", ".").toDoubleOrNull()
            ?: throw ValidationException("cantidad", "debe ser un numero valido")
        if (cantidad <= 0) throw ValidationException("cantidad", "debe ser mayor que 0")
        return cantidad
    }

    fun validarFecha(valor: String): LocalDate {
        return try {
            LocalDate.parse(valor)
        } catch (e: Exception) {
            throw ValidationException("fecha", "formato esperado: YYYY-MM-DD")
        }
    }

    fun validarDescripcion(valor: String): String {
        val limpia = valor.trim()
        if (limpia.length > 200) throw ValidationException("descripcion", "maximo 200 caracteres")
        return limpia
    }

    fun validarNombreCategoria(valor: String): String {
        val limpia = valor.trim()
        if (limpia.isBlank()) throw ValidationException("nombre", "no puede estar vacio")
        if (limpia.length > 50) throw ValidationException("nombre", "maximo 50 caracteres")
        return limpia
    }
}
