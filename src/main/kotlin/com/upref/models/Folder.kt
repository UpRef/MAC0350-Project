package com.upref.models
import org.jetbrains.exposed.sql.*

data class Folder (
    var id: Integer,
    var title: String
)

object Folders : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)

    override val primaryKey = PrimaryKey(id)
}


/*
    constructor(id: String,

                title: String,
                authors: MutableList<String>,
                abstract: String,
                keywords: MutableList<String>,
                links: MutableList<String>) {
        this.id = id
        this.title = title
        this.authors = authors
        this.abstract = abstract
        this.links = links
        this.keywords = keywords
    }

    fun search(by: String, query: String): Paper {
        TODO("we don't have a database yet.")
    }
}
*/