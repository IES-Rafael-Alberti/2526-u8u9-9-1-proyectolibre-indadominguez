package app

import repository.memory.CategoriaRepositoryMemory
import repository.memory.GastoRepositoryMemory
import service.CategoriaService
import service.GastoService
import java.time.LocalDate

fun main() {

    val categoriaService = CategoriaService(CategoriaRepositoryMemory())
    val gastoService = GastoService(GastoRepositoryMemory())

    var salir = false

    while (!salir) {
        println("\n===== EXPENSE TRACKER =====")
        println("1. Crear categoría")
        println("2. Listar categorías")
        println("3. Crear gasto")
        println("4. Listar gastos")
        println("5. Eliminar gasto")
        println("6. Salir")
        print("Selecciona una opción: ")

        when (readln().toIntOrNull()) {

            1 -> {
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

            2 -> {
                val categorias = categoriaService.listarTodas()
                if (categorias.isEmpty()) {
                    println("No hay categorías")
                } else {
                    categorias.forEach { println(it) }
                }
            }

            3 -> {
                try {
                    print("Descripción: ")
                    val descripcion = readln()

                    print("Monto: ")
                    val monto = readln().toDouble()

                    print("Fecha (YYYY-MM-DD): ")
                    val fecha = LocalDate.parse(readln())

                    print("ID categoría: ")
                    val categoriaId = readln().toLong()

                    val gasto = gastoService.registrar(descripcion, monto, fecha, categoriaId)
                    println("✔ Gasto creado: $gasto")

                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }

            4 -> {
                val gastos = gastoService.listarTodos()
                if (gastos.isEmpty()) {
                    println("No hay gastos")
                } else {
                    gastos.forEach { println(it) }
                }
            }

            5 -> {
                try {
                    print("ID del gasto a eliminar: ")
                    val id = readln().toLong()
                    gastoService.eliminar(id)
                    println("✔ Gasto eliminado")
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }

            6 -> {
                println("Saliendo...")
                salir = true
            }

            else -> println("Opción inválida")
        }
    }
}