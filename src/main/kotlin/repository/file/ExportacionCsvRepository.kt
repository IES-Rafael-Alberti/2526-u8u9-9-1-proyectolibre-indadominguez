package repository.file

import exception.PersistenciaException
import model.Gasto
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class ExportacionCsvRepository {

    fun exportarGastos(gastos: List<Gasto>, ruta: String) {
        val path: Path = Paths.get(ruta)
        try {
            Files.createDirectories(path.parent)
            PrintWriter(path.toFile()).use { writer ->
                writer.println("ID,Descripcion,Monto,Fecha,CategoriaID")
                gastos.forEach { gasto ->
                    writer.println("${gasto.id},${gasto.descripcion},${gasto.monto},${gasto.fecha},${gasto.categoriaId}")
                }
            }
        } catch (e: Exception) {
            throw PersistenciaException("Error al exportar gastos a CSV en $ruta", e)
        }
    }
}