package service

import model.LogEvento
import repository.ILogRepository
import repository.file.LogFileRepository

class LogService(
    private val logMongoRepository: ILogRepository,
    private val logFileRepository: LogFileRepository,
    private val logFilePath: String
) {

    fun registrar(tipo: String, mensaje: String): LogEvento {
        val log = LogEvento(tipo = tipo, mensaje = mensaje)

        try {
            logMongoRepository.save(log)
        } catch (e: Exception) {
            println("Mongo log error: ${e.message}")
        }

        try {
            logFileRepository.guardar(log, logFilePath)
        } catch (e: Exception) {
            println("File log error: ${e.message}")
        }

        return log
    }

    fun listarTodos(): List<LogEvento> {
        return logMongoRepository.findAll()
    }

    fun listarPorTipo(tipo: String): List<LogEvento> {
        return logMongoRepository.findByTipo(tipo)
    }

    fun leerLogsDeArchivo(): List<String> {
        return logFileRepository.leerTodos(logFilePath)
    }
}
