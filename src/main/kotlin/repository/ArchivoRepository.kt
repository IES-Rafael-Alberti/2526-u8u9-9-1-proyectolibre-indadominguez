package repository

import exception.RepositoryException
import model.Gasto
import java.nio.file.Files
import java.nio.file.Path

class ArchivoRepository(ruta: Path = Path.of("data")) {

    private val configPath = ruta.resolve("config.properties")
    private val exportPath = ruta.resolve("gastos_export.csv")

    init {
        Files.createDirectories(ruta)
    }

    fun cargarConfig(): Map<String, String> {
        val config = mutableMapOf<String, String>()
        if (!Files.exists(configPath)) return config
        try {
            configPath.toFile().bufferedReader().useLines { lineas ->
                lineas.forEach { linea ->
                    val limpia = linea.trim()
                    if (limpia.isNotEmpty() && !limpia.startsWith("#")) {
                        val partes = limpia.split("=", limit = 2)
                        if (partes.size == 2) config[partes[0].trim()] = partes[1].trim()
                    }
                }
            }
        } catch (e: Exception) {
            throw RepositoryException("Error al cargar configuracion", e)
        }
        return config
    }

    fun guardarConfig(config: Map<String, String>) {
        try {
            configPath.toFile().bufferedWriter().use { writer ->
                config.forEach { (k, v) -> writer.write("$k=$v\n") }
            }
        } catch (e: Exception) {
            throw RepositoryException("Error al guardar configuracion", e)
        }
    }

    fun exportarCSV(gastos: List<Gasto>, nomCategoria: (Long?) -> String) {
        try {
            exportPath.toFile().bufferedWriter().use { writer ->
                writer.write("ID,Fecha,Cantidad,Descripcion,Categoria\n")
                gastos.forEach { g ->
                    val cat = if (g.categoriaId != null) nomCategoria(g.categoriaId) else ""
                    writer.write("${g.id},${g.fecha},${g.cantidad},${g.descripcion},$cat\n")
                }
            }
            println("Gastos exportados a $exportPath")
        } catch (e: Exception) {
            throw RepositoryException("Error al exportar CSV", e)
        }
    }
}
