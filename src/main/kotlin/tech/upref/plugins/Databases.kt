package tech.upref.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import tech.upref.schemas.*

fun Application.configureDatabases() {
    val dbConnection: Connection = connectToPostgres(embedded = true)
    val paperService = PaperService(dbConnection)

    routing {
        // Create paper
        post("/papers") {
            val paper = call.receive<Paper>()
            val id = paperService.create(paper)
            call.respond(HttpStatusCode.Created, id)
        }

        // Read paper
        get("/papers/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")

            try {
                val paper = paperService.read(id)
                call.respond(HttpStatusCode.OK, paper)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Search paper
        get("/search/{category}/{query}") {
            val query = call.parameters["query"]?.toString() ?: throw IllegalArgumentException("Invalid query")
            val category = call.parameters["category"]?.toString() ?: throw IllegalArgumentException("Invalid category")

            try {
                val papers = paperService.search(query, category)
                call.respond(HttpStatusCode.OK, papers)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Update paper
        put("/papers/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val paper = call.receive<Paper>()
            paperService.update(id, paper)
            call.respond(HttpStatusCode.OK)
        }

        // Delete paper
        delete("/papers/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            paperService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }

    val folderService = FolderService(dbConnection)
    routing {
        // Create folder
        post("/folders") {
            val folder = call.receive<Folder>()
            println(folder);
            val id = folderService.create(folder)
            call.respond(HttpStatusCode.Created, id)
        }

        // Read folder
        get("/folders/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")

            try {
                val folder = folderService.read(id)
                call.respond(HttpStatusCode.OK, folder)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Update folder
        put("/folders/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val folder = call.receive<Folder>()
            folderService.update(id, folder)
            call.respond(HttpStatusCode.OK)
        }

        // Delete folder
        delete("/folders/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            folderService.delete(id)
            call.respond(HttpStatusCode.OK)
        }

        // Get Folders From User
        get("/folders/from/user/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")

            try {
                val folders = folderService.getFoldersFromUser(id)
                call.respond(HttpStatusCode.OK, folders)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }


    val paperFolderService = PaperFolderService(dbConnection)
    routing {
        // Insert paper in folder
        post("/folders/insert/paper") {
            val paperFolder = call.receive<PaperFolder>()
            val id = paperFolderService.create(paperFolder)
            call.respond(HttpStatusCode.Created, id)
        }

        // Read folder
        get("/papers/from/folder/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")

            try {
                val papers = paperFolderService.read(id)
                call.respond(HttpStatusCode.OK, papers)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Read folder
        get("/papers/ids/from/folder/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")

            try {
                val papers = paperFolderService.read_ids(id)
                call.respond(HttpStatusCode.OK, papers)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Update folder
        put("/folders/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val folder = call.receive<Folder>()
            folderService.update(id, folder)
            call.respond(HttpStatusCode.OK)
        }

        // Delete folder
        delete("/delete/paper/{paper_id}/from/folder/{folder_id}") {
            val folder_id = call.parameters["folder_id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val paper_id = call.parameters["paper_id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            paperFolderService.delete(paper_id, folder_id)
            call.respond(HttpStatusCode.OK)
        }
    }

    val userService = UserService(dbConnection)
    routing {
        // Create user
        post("/users") {
            val user = call.receive<User>()
            val id = userService.create(user)
            call.respond(HttpStatusCode.Created, id)
        }

        // Read user
        get("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")

            try {
                val user = userService.read(id)
                call.respond(HttpStatusCode.OK, user)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Update user
        put("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = call.receive<User>()
            userService.update(id, user)
            call.respond(HttpStatusCode.OK)
        }

        // Delete city
        delete("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            userService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }


}

/**
 * Makes a connection to a Postgres database.
 *
 * In order to connect to your running Postgres process,
 * please specify the following parameters in your configuration file:
 * - postgres.url -- Url of your running database process.
 * - postgres.user -- Username for database connection
 * - postgres.password -- Password for database connection
 *
 * If you don't have a database process running yet, you may need to [download]((https://www.postgresql.org/download/))
 * and install Postgres and follow the instructions [here](https://postgresapp.com/).
 * Then, you would be able to edit your url,  which is usually "jdbc:postgresql://host:port/database", as well as
 * user and password values.
 *
 *
 * @param embedded -- if [true] defaults to an embedded database for tests that runs locally in the same process.
 * In this case you don't have to provide any parameters in configuration file, and you don't have to run a process.
 *
 * @return [Connection] that represent connection to the database. Please, don't forget to close this connection when
 * your application shuts down by calling [Connection.close]
 * */


fun Application.connectToPostgres(embedded: Boolean): Connection {
    Class.forName("org.postgresql.Driver")
    if (embedded) {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
    } else {
        val url = environment.config.property("postgres.url").getString()
        val user = environment.config.property("postgres.user").getString()
        val password = environment.config.property("postgres.password").getString()

        return DriverManager.getConnection(url, user, password)
    }
}
