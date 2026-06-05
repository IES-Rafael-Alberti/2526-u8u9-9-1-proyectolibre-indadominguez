import repository.mongo.MongoManager



fun main() {

    val database = MongoManager.getDatabase()

    println("✔ Conectado a Mongo")
    println("Base de datos: ${database.name}")

}