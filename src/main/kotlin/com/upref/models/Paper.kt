package com.upref.models
import org.jetbrains.exposed.sql.*

data class Paper (
    var id: Integer,
    var title: String,
    var authors: MutableList<String>,
    var abstract: String,
    var keywords: MutableList<String>,
    var links: MutableList<String>
)

object Papers : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val authors = varchar("authors", 1024)
    val abstract = varchar("abstract", 2048) // TODO: verificar se precisa aumentar isso
    val keywords = varchar("keywords", 128)
    val links = varchar("links", 128)

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