package repository

interface BaseRepository<T, ID> {
    fun listar(): List<T>
    fun buscar(id: ID): T?
    fun guardar(entidad: T): T
    fun actualizar(entidad: T): T
    fun eliminar(id: ID): Boolean
}
