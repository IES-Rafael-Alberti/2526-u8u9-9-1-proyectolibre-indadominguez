package repository

import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import exception.RepositoryException
import model.LogEvento
import org.bson.Document

class MongoRepository(
    private val cadenaConexion: String = "mongodb://localhost:27017",
    private val bd: String = "gestor_gastos"
) {
    private val cliente = MongoClients.create(ConnectionString(cadenaConexion))
    private val coleccion = cliente.getDatabase(bd).getCollection("logs")

    fun insertar(evento: LogEvento): LogEvento {
        try {
            val doc = Document()
                .append("tipo", evento.tipo)
                .append("descripcion", evento.descripcion)
                .append("fecha", evento.fecha.toString())
            coleccion.insertOne(doc)
            return evento.copy(id = doc.getObjectId("_id").toHexString())
        } catch (e: Exception) {
            throw RepositoryException("Error al insertar log en MongoDB", e)
        }
    }

    fun listar(): List<LogEvento> {
        return try {
            coleccion.find().map { doc ->
                LogEvento(
                    id = doc.getObjectId("_id").toHexString(),
                    tipo = doc.getString("tipo"),
                    descripcion = doc.getString("descripcion"),
                    fecha = java.time.LocalDateTime.parse(doc.getString("fecha"))
                )
            }.toList()
        } catch (e: Exception) {
            throw RepositoryException("Error al listar logs de MongoDB", e)
        }
    }

    fun cerrar() {
        try { cliente.close() } catch (e: Exception) { throw RepositoryException("Error al cerrar MongoDB", e) }
    }
}
