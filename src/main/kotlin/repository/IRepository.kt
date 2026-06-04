package repository

interface IRepository<T, ID> {
    fun save(entity: T): T
    fun findById(id: ID): T?
    fun findAll(): List<T>
    fun update(entity: T): T
    fun delete(id: ID): Boolean
}