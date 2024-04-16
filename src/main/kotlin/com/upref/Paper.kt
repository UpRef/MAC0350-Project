package com.upref

class Paper {
    var id: String
    var title: String
    var authors: MutableList<String>
    var abstract: String
    var keywords: MutableList<String>
    var links: MutableList<String>

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
