package com.upref.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Main Page")
        }

        route("/search") {
            get("/title/{title}") {
                val title = call.parameters["title"]
                call.respondText("Search by title " + title)
            }

            get("/author/{author}") {
                val author = call.parameters["author"]
                call.respondText("Search by author name " + author)
            }

            get("/keyword/{keyword}") {
                val keyword = call.parameters["keyword"]
                call.respondText("Search by keyword " + keyword)
                // connectDB, fazer queries
            }
        }

        get("/paper/{paper_id}") {
            val paper_id = call.parameters["paper_id"]
            call.respondText("This is paper " + paper_id)
        }

        get("/folder/{folder_id}") {
            val folder_id = call.parameters["folder_id"]
            call.respondText("This is folder " + folder_id)
        }

    }
}


