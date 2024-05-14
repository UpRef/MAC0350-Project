package com.upref.dao
import com.typesafe.config.ConfigException.Null
import com.upref.dao.DatabaseSingleton.dbQuery
import com.upref.models.*

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToPaper(row: ResultRow) = Paper(
        id = row[Papers.id],
        title = row[Papers.title],
        authors = row[Papers.authors],
        abstract = row[Papers.abstract],
        keywords = row[Papers.keywords],
        links = row[Papers.links]
    )

    override suspend fun allPapers(): List<Paper> = dbQuery {
        Papers.selectAll().map(::resultRowToPaper)
    }

    override suspend fun paper(id: Int): Paper? =  dbQuery {
        Papers
            .select { Papers.id eq id}
            .map(::resultRowToPaper)
            .singleOrNull()
    }

    override suspend fun searchPapersByTitle(query: String?): List<Paper>? =  dbQuery {
        val queryNotNull = query!!
        Papers
            .select { Papers.title like '%'+queryNotNull+'%'}
            .map(::resultRowToPaper)
    }

    override suspend fun searchPapersByAuthor(query: String?): List<Paper>? =  dbQuery {
        val queryNotNull = query!!
        Papers
            .select { Papers.authors like '%'+queryNotNull+'%'}
            .map(::resultRowToPaper)
    }

    override suspend fun searchPapersByKeyword(query: String?): List<Paper>? =  dbQuery {
        val queryNotNull = query!!
        Papers
            .select { Papers.keywords like '%'+queryNotNull+'%'}
            .map(::resultRowToPaper)
    }

    override suspend fun addNewPaper(title: String, authors: String,
                                     abstract: String, keywords: String,
                                     links: String): Paper? = dbQuery {
        val insertStatement = Papers.insert {
            it[Papers.title] = title
            it[Papers.authors] = authors
            it[Papers.abstract] = abstract
            it[Papers.keywords] = keywords
            it[Papers.links] = links
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPaper)
    }

    override suspend fun editPaper(id: Int, title: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletePaper(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(allPapers().isEmpty()) {
            addNewPaper("Attention Is All You Need", "authors", "abstract", "keywords", "links")
        }
    }
}