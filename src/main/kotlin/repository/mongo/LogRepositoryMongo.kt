package repository.mongo

import model.LogEvento
import org.bson.Document
import repository.ILogRepository
import java.time.LocalDateTime

class LogRepositoryMongo : ILogRepository {

    private val collection = MongoManager
        .getDatabase()
        .getCollection("logs")

    override fun save(entity: LogEvento): LogEvento {
        val doc = Document()
        doc["_id"] = entity.id
        doc["tipo"] = entity.tipo
        doc["mensaje"] = entity.mensaje
        doc["fecha"] = entity.fecha.toString()

        collection.insertOne(doc)
        return entity
    }

    override fun findAll(): List<LogEvento> {
        val logs = mutableListOf<LogEvento>()

        val result = collection.find()

        for (doc in result) {
            logs.add(
                LogEvento(
                    id = doc.getString("_id"),
                    tipo = doc.getString("tipo"),
                    mensaje = doc.getString("mensaje"),
                    fecha = LocalDateTime.parse(doc.getString("fecha"))
                )
            )
        }

        return logs
    }

    override fun findById(id: String): LogEvento? = null
    override fun update(entity: LogEvento): LogEvento = entity
    override fun delete(id: String) {}
    override fun findByTipo(tipo: String): List<LogEvento> = emptyList()
}