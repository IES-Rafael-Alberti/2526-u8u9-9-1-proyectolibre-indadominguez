package repository.memory

import model.Gasto
import repository.IGastoRepository

class GastoRepositoryMemory : IGastoRepository {

    private val gastos = mutableListOf<Gasto>()
    private var idCounter = 1L

    override fun save(entity: Gasto): Gasto {
        val nuevo = entity.copy(id = idCounter++)
        gastos.add(nuevo)
        return nuevo
    }

    override fun findById(id: Long): Gasto? {
        return gastos.find { it.id == id }
    }

    override fun findAll(): List<Gasto> {
        return gastos
    }

    override fun update(entity: Gasto): Gasto {
        val index = gastos.indexOfFirst { it.id == entity.id }
        if (index != -1) {
            gastos[index] = entity
        }
        return entity
    }

    override fun delete(id: Long) {
        gastos.removeIf { it.id == id }
    }

    override fun findByCategoriaId(categoriaId: Long): List<Gasto> {
        return gastos.filter { it.categoriaId == categoriaId }
    }

    override fun findByMontoGreaterThan(monto: Double): List<Gasto> {
        return gastos.filter { it.monto > monto }
    }
}