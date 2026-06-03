package repository.file

import exception.PersistenciaException
import model.LogEvento
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class LogFileRepository {

    fun guardar(log: LogEvento, ruta: String) {
        try {
            Files.createDirectories(Paths.get(ruta).parent)
            val linea = "[${log.fecha}] [${log.tipo}] ${log.mensaje}${System.lineSeparator()}"
            Files.writeString(
                Paths.get(ruta),
                linea,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            )
        } catch (e: Exception) {
            throw PersistenciaException("Error al guardar log en archivo $ruta", e)
        }
    }

    fun leerTodos(ruta: String): List<String> {
        try {
            val path = Paths.get(ruta)
            return if (Files.exists(path)) {
                Files.readAllLines(path)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            throw PersistenciaException("Error al leer logs desde $ruta", e)
        }
    }
}
