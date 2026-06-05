package repository.mongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

object MongoManager {

    private const val USER = "idomhit1411"

    private val PASSWORD = System.getenv("MONGO_PASS")

    private const val CLUSTER =
        "cluster0.vbqbe9r.mongodb.net/?retryWrites=true&w=majority"

    private val URI =
        "mongodb+srv://$USER:$PASSWORD@$CLUSTER"

    private val client: MongoClient = MongoClients.create(URI)

    fun getDatabase(): MongoDatabase {
        return client.getDatabase("expensetracker")
    }
}