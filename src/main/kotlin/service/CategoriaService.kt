package service

import exception.ValidacionException
import model.Categoria
import repository.ICategoriaRepository
import validator.CategoriaValidator

class CategoriaService(private val categoriaRepository: ICategoriaRepository) {

    fun registrar(nombre: String, descripcion: String): Categoria {
        val categoria = Categoria(nombre = nombre, descripcion = descripcion)
        CategoriaValidator.validar(categoria)
        val existente = categoriaRepository.findByNombre(nombre)
        if (existente != null) {
            throw ValidacionException("Ya existe una categoría con el nombre '$nombre'")
        }
        return categoriaRepository.save(categoria)
    }

    fun buscarPorId(id: Long): Categoria? {
        return categoriaRepository.findById(id)
    }

    fun listarTodas(): List<Categoria> {
        return categoriaRepository.findAll()
    }

    fun actualizar(id: Long, nombre: String, descripcion: String): Categoria {
        val existente = categoriaRepository.findById(id)
            ?: throw ValidacionException("No existe categoría con id $id")
        val actualizada = existente.copy(nombre = nombre, descripcion = descripcion)
        CategoriaValidator.validar(actualizada)
        return categoriaRepository.update(actualizada)
    }

    fun eliminar(id: Long) {
        categoriaRepository.delete(id)
    }
}
