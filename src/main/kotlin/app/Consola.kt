package app

import repository.sql.DataBase
import repository.sql.CategoriaRepositorySQL
import repository.sql.GastoRepositorySQL
import service.CategoriaService
import service.GastoService
import java.time.LocalDate

class Consola {

    private val connection = DataBase.getConnection()

    private val categoriaService = CategoriaService(
        CategoriaRepositorySQL(connection)
    )

    private val gastoService = GastoService(
        GastoRepositorySQL(connection)
    )

    fun run() {
        var salir = false

        while (!salir) {
            mostrarMenu()

            when (leerOpcion()) {
                1 -> crearCategoria()
                2 -> listarCategorias()
                3 -> crearGasto()
                4 -> listarGastos()
                5 -> eliminarGasto()
                6 -> salir = true
                else -> println("Opción inválida")
            }
        }

        println("Saliendo...")
    }

    private fun mostrarMenu() {
        println("\n===== EXPENSE TRACKER (SQL) =====")
        println("1. Crear categoría")
        println("2. Listar categorías")
        println("3. Crear gasto")
        println("4. Listar gastos")
        println("5. Eliminar gasto")
        println("6. Salir")
        print("Selecciona una opción: ")
    }

    private fun leerOpcion(): Int {
        return readln().toIntOrNull() ?: -1
    }

    private fun crearCategoria() {
        try {
            print("Nombre: ")
            val nombre = readln()

            print("Descripción: ")
            val descripcion = readln()

            val categoria = categoriaService.registrar(nombre, descripcion)
            println("✔ Categoría creada: $categoria")

        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun listarCategorias() {
        val categorias = categoriaService.listarTodas()

        if (categorias.isEmpty()) {
            println("No hay categorías")
        } else {
            categorias.forEach { println(it) }
        }
    }

    private fun crearGasto() {
        try {
            print("Descripción: ")
            val descripcion = readln()

            print("Monto: ")
            val monto = readln().toDouble()

            print("Fecha (YYYY-MM-DD): ")
            val fecha = LocalDate.parse(readln())

            print("ID categoría: ")
            val categoriaId = readln().toLong()

            val gasto = gastoService.registrar(
                descripcion,
                monto,
                fecha,
                categoriaId
            )

            println("✔ Gasto creado: $gasto")

        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun listarGastos() {
        val gastos = gastoService.listarTodos()

        if (gastos.isEmpty()) {
            println("No hay gastos")
        } else {
            gastos.forEach { println(it) }
        }
    }

    private fun eliminarGasto() {
        try {
            print("ID del gasto a eliminar: ")
            val id = readln().toLong()

            gastoService.eliminar(id)

            println("✔ Gasto eliminado")

        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}