package com.upref.plugins

import freemarker.cache.*
import com.upref.dao.dao
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.freemarker.*

fun Application.configureRouting() {
    routing {
        get ("/") {
            call.respondText("main page")
        }

        get("/all") {
            call.respondText(mapOf("papers" to dao.allPapers()).toString())
        }

        get("/add") {
            val paper = dao.addNewPaper("paper_title", "paper_authors", "paper_abstract", "paper_keywords", "paper_links")
            call.respondRedirect("/paper/${paper?.id}")
        }

        route("/search/") {
            get("/title={title}") {
                val title = call.parameters["title"]
                val response =  mapOf("papers" to dao.searchPapersByTitle(title)).toString()
                call.respondText(response)
            }

            get("/author={author}") {
                val author = call.parameters["author"]
                val response = mapOf("papers" to dao.searchPapersByAuthor(author)).toString()
                call.respondText(response)
            }

            get("/keyword={keyword}") {
                val keyword = call.parameters["keyword"]
                val response = mapOf("papers" to dao.searchPapersByKeyword(keyword)).toString()
                call.respondText(response)
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


