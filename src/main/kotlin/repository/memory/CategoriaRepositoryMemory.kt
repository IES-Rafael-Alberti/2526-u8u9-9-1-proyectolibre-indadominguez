package repository.memory

import model.Categoria
import repository.ICategoriaRepository

class CategoriaRepositoryMemory : ICategoriaRepository {

    private val categorias = mutableListOf<Categoria>()
    private var idCounter = 1L

    override fun save(entity: Categoria): Categoria {
        val nueva = entity.copy(id = idCounter++)
        categorias.add(nueva)
        return nueva
    }

    override fun findById(id: Long): Categoria? {
        return categorias.find { it.id == id }
    }

    override fun findAll(): List<Categoria> {
        return categorias
    }

    override fun update(entity: Categoria): Categoria {
        val index = categorias.indexOfFirst { it.id == entity.id }
        if (index != -1) {
            categorias[index] = entity
        }
        return entity
    }

    override fun delete(id: Long) {
        categorias.removeIf { it.id == id }
    }

    override fun findByNombre(nombre: String): Categoria? {
        return categorias.find { it.nombre.equals(nombre, ignoreCase = true) }
    }
}