import org.h2.tools.Server
import repository.sql.DataBase
import app.Consola

fun main() {

    val server = Server.createWebServer("-web", "-webAllowOthers").start()
    println("H2 Web disponible en: ${server.url}")

    DataBase.getConnection().use { }

    Consola().run()

    // MUY IMPORTANTE
    server.stop()
    println("Servidor H2 detenido")
}