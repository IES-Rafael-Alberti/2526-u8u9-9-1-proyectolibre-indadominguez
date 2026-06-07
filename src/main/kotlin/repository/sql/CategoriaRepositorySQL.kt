package repository.sql

import model.Categoria
import repository.ICategoriaRepository
import java.sql.Connection
import java.sql.Statement

class CategoriaRepositorySQL(
    private val connection: Connection
) : ICategoriaRepository {

    override fun save(entity: Categoria): Categoria {
        val sql = "INSERT INTO CATEGORIAS (NOMBRE, DESCRIPCION) VALUES (?, ?)"

        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stmt ->
            stmt.setString(1, entity.nombre)
            stmt.setString(2, entity.descripcion)

            stmt.executeUpdate()

            val rs = stmt.generatedKeys
            rs.next()

            return entity.copy(id = rs.getLong(1))
        }
    }

    override fun findById(id: Long): Categoria? {
        val sql = "SELECT * FROM CATEGORIAS WHERE ID = ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setLong(1, id)

            val rs = stmt.executeQuery()

            return if (rs.next()) {
                Categoria(
                    id = rs.getLong("ID"),
                    nombre = rs.getString("NOMBRE"),
                    descripcion = rs.getString("DESCRIPCION")
                )
            } else null
        }
    }

    override fun findAll(): List<Categoria> {
        val lista = mutableListOf<Categoria>()
        val sql = "SELECT * FROM CATEGORIAS"

        connection.createStatement().use { stmt ->
            val rs = stmt.executeQuery(sql)

            while (rs.next()) {
                lista.add(
                    Categoria(
                        id = rs.getLong("ID"),
                        nombre = rs.getString("NOMBRE"),
                        descripcion = rs.getString("DESCRIPCION")
                    )
                )
            }
        }

        return lista
    }

    override fun update(entity: Categoria): Categoria {
        val sql = "UPDATE CATEGORIAS SET NOMBRE = ?, DESCRIPCION = ? WHERE ID = ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, entity.nombre)
            stmt.setString(2, entity.descripcion)
            stmt.setLong(3, entity.id)

            stmt.executeUpdate()
        }

        return entity
    }

    override fun delete(id: Long) {
        val sql = "DELETE FROM CATEGORIAS WHERE ID = ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setLong(1, id)
            stmt.executeUpdate()
        }
    }

    override fun findByNombre(nombre: String): Categoria? {
        val sql = "SELECT * FROM CATEGORIAS WHERE LOWER(NOMBRE) = LOWER(?)"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, nombre)

            val rs = stmt.executeQuery()

            return if (rs.next()) {
                Categoria(
                    id = rs.getLong("ID"),
                    nombre = rs.getString("NOMBRE"),
                    descripcion = rs.getString("DESCRIPCION")
                )
            } else null
        }
    }
}