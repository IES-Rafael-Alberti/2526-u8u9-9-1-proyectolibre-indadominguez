package repository

import model.Categoria

interface ICategoriaRepository : IRepository<Categoria, Long> {
    fun findByNombre(nombre: String): Categoria?
}