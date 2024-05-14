package com.upref.dao

import com.upref.models.Paper

interface DAOFacade {
    suspend fun allPapers(): List<Paper>
    suspend fun paper(id: Int): Paper?
    suspend fun addNewPaper(title: String, authors: String,
                            abstract: String, keywords: String,
                            links: String): Paper?

    suspend fun searchPapersByTitle(query: String?): List<Paper>?
    suspend fun searchPapersByAuthor(query: String?): List<Paper>?
    suspend fun searchPapersByKeyword(query: String?): List<Paper>?
    suspend fun editPaper(id: Int, title: String): Boolean
    suspend fun deletePaper(id: Int): Boolean
}