package validator

import exception.ValidacionException
import model.Categoria


object CategoriaValidator {

    private val NOMBRE_REGEX = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,100}$")

    fun validar(categoria: Categoria) {
        val errores = mutableListOf<String>()

        if (categoria.nombre.isBlank()) {
            errores.add("El nombre de la categoría no puede estar vacío")
        } else if (!NOMBRE_REGEX.matches(categoria.nombre)) {
            errores.add("El nombre debe tener entre 3 y 100 caracteres alfabéticos")
        }

        if (categoria.descripcion.length > 500) {
            errores.add("La descripción no puede superar los 500 caracteres")
        }

        if (errores.isNotEmpty()) {
            throw ValidacionException("Error de validación de categoría: ${errores.joinToString("; ")}")
        }
    }
}