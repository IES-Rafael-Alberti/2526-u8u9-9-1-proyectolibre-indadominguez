package repository.sql

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DataBase {

    private const val URL = "jdbc:h2:./data/expensetracker"
    private const val USER = "sa"
    private const val PASSWORD = ""

    init {
        try {
            Class.forName("org.h2.Driver")
            initDatabase()
        } catch (e: Exception) {
            throw RuntimeException("Error inicializando la base de datos", e)
        }
    }

    fun getConnection(): Connection {
        return try {
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: SQLException) {
            throw RuntimeException("Error al conectar con la base de datos", e)
        }
    }

    private fun initDatabase() {
        getConnection().use { connection ->

            connection.createStatement().use { statement ->

                // =========================
                // TABLA CATEGORIAS
                // =========================
                statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS CATEGORIAS (
                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        NOMBRE VARCHAR(100) NOT NULL UNIQUE,
                        DESCRIPCION VARCHAR(500)
                    )
                    """.trimIndent()
                )

                // =========================
                // TABLA GASTOS
                // =========================
                statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS GASTOS (
                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        DESCRIPCION VARCHAR(200) NOT NULL,
                        MONTO DOUBLE NOT NULL,
                        FECHA DATE NOT NULL,
                        CATEGORIA_ID BIGINT NOT NULL,
                        CONSTRAINT FK_GASTO_CATEGORIA
                            FOREIGN KEY (CATEGORIA_ID)
                            REFERENCES CATEGORIAS(ID)
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
            }
        }
    }
}