package repository

import model.Gasto

interface IGastoRepository : IRepository<Gasto, Long> {
    fun findByCategoriaId(categoriaId: Long): List<Gasto>
    fun findByMontoGreaterThan(monto: Double): List<Gasto>
}