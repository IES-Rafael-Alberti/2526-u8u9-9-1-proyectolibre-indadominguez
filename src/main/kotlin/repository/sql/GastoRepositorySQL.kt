package repository.sql

import model.Gasto
import repository.IGastoRepository
import java.sql.Connection
import java.sql.Statement
import java.sql.Date

class GastoRepositorySQL(
    private val connection: Connection
) : IGastoRepository {

    override fun save(entity: Gasto): Gasto {
        val sql = """
            INSERT INTO GASTOS (DESCRIPCION, MONTO, FECHA, CATEGORIA_ID)
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stmt ->
            stmt.setString(1, entity.descripcion)
            stmt.setDouble(2, entity.monto)
            stmt.setDate(3, Date.valueOf(entity.fecha))
            stmt.setLong(4, entity.categoriaId)

            stmt.executeUpdate()

            val rs = stmt.generatedKeys
            rs.next()

            return entity.copy(id = rs.getLong(1))
        }
    }

    override fun findById(id: Long): Gasto? {
        val sql = "SELECT * FROM GASTOS WHERE ID = ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setLong(1, id)

            val rs = stmt.executeQuery()

            return if (rs.next()) {
                Gasto(
                    id = rs.getLong("ID"),
                    descripcion = rs.getString("DESCRIPCION"),
                    monto = rs.getDouble("MONTO"),
                    fecha = rs.getDate("FECHA").toLocalDate(),
                    categoriaId = rs.getLong("CATEGORIA_ID")
                )
            } else null
        }
    }

    override fun findAll(): List<Gasto> {
        val lista = mutableListOf<Gasto>()
        val sql = "SELECT * FROM GASTOS"

        connection.createStatement().use { stmt ->
            val rs = stmt.executeQuery(sql)

            while (rs.next()) {
                lista.add(
                    Gasto(
                        id = rs.getLong("ID"),
                        descripcion = rs.getString("DESCRIPCION"),
                        monto = rs.getDouble("MONTO"),
                        fecha = rs.getDate("FECHA").toLocalDate(),
                        categoriaId = rs.getLong("CATEGORIA_ID")
                    )
                )
            }
        }

        return lista
    }

    override fun update(entity: Gasto): Gasto {
        val sql = """
            UPDATE GASTOS
            SET DESCRIPCION = ?, MONTO = ?, FECHA = ?, CATEGORIA_ID = ?
            WHERE ID = ?
        """.trimIndent()

        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, entity.descripcion)
            stmt.setDouble(2, entity.monto)
            stmt.setDate(3, Date.valueOf(entity.fecha))
            stmt.setLong(4, entity.categoriaId)
            stmt.setLong(5, entity.id)

            stmt.executeUpdate()
        }

        return entity
    }

    override fun delete(id: Long) {
        val sql = "DELETE FROM GASTOS WHERE ID = ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setLong(1, id)
            stmt.executeUpdate()
        }
    }

    override fun findByCategoriaId(categoriaId: Long): List<Gasto> {
        val lista = mutableListOf<Gasto>()
        val sql = "SELECT * FROM GASTOS WHERE CATEGORIA_ID = ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setLong(1, categoriaId)

            val rs = stmt.executeQuery()

            while (rs.next()) {
                lista.add(
                    Gasto(
                        id = rs.getLong("ID"),
                        descripcion = rs.getString("DESCRIPCION"),
                        monto = rs.getDouble("MONTO"),
                        fecha = rs.getDate("FECHA").toLocalDate(),
                        categoriaId = rs.getLong("CATEGORIA_ID")
                    )
                )
            }
        }

        return lista
    }

    override fun findByMontoGreaterThan(monto: Double): List<Gasto> {
        val lista = mutableListOf<Gasto>()
        val sql = "SELECT * FROM GASTOS WHERE MONTO > ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setDouble(1, monto)

            val rs = stmt.executeQuery()

            while (rs.next()) {
                lista.add(
                    Gasto(
                        id = rs.getLong("ID"),
                        descripcion = rs.getString("DESCRIPCION"),
                        monto = rs.getDouble("MONTO"),
                        fecha = rs.getDate("FECHA").toLocalDate(),
                        categoriaId = rs.getLong("CATEGORIA_ID")
                    )
                )
            }
        }

        return lista
    }
}