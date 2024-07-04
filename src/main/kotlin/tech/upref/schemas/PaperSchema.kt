package tech.upref.schemas

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.Statement

@Serializable
data class Paper(val arxiv_id: String, val title: String, val authors: String, val abstract: String, val doi: String, val update_date: String, val creation_date: String)
class PaperService(private val connection: Connection) {
    companion object {
        private const val CREATE_TABLE_PAPERS =
            "CREATE TABLE PAPERS (ID SERIAL PRIMARY KEY, ARXIV_ID TEXT, TITLE TEXT, AUTHORS TEXT, ABSTRACT TEXT, DOI TEXT, UPDATE_DATE TEXT, CREATION_DATE TEXT);"
        private const val SELECT_PAPER_BY_ID = "SELECT arxiv_id, title, authors, abstract, doi, update_date, creation_date FROM papers WHERE id = ?"
        private const val SELECT_PAPERS_TITLE_LIKE = "SELECT arxiv_id, title, authors, abstract, doi, update_date, creation_date FROM papers WHERE title LIKE ?"
        private const val SELECT_PAPERS_AUTHORS_LIKE = "SELECT arxiv_id, title, authors, abstract, doi, update_date, creation_date FROM papers WHERE authors LIKE ?"
        private const val SELECT_PAPERS_ABSTRACT_LIKE = "SELECT arxiv_id, title, authors, abstract, doi, update_date, creation_date FROM papers WHERE abstract LIKE ?"
        private const val INSERT_PAPER = "INSERT INTO papers (arxiv_id, title, authors, abstract, doi, update_date, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?)"
        private const val UPDATE_PAPER = "UPDATE papers SET update_date = ?, abstract = ? WHERE id = ?"
        private const val DELETE_PAPER = "DELETE FROM papers WHERE id = ?"
    }

    init {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_PAPERS)
    }

    private var newPaperId = 0

    // Create new paper
    suspend fun create(paper: Paper): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_PAPER, Statement.RETURN_GENERATED_KEYS)

        statement.setString(1, paper.arxiv_id)
        statement.setString(2, paper.title)
        statement.setString(3, paper.authors)
        statement.setString(4, paper.abstract)
        statement.setString(5, paper.doi)
        statement.setString(6, paper.update_date)
        statement.setString(7, paper.creation_date)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw Exception("Unable to retrieve the id of the newly inserted paper")
        }
    }

    // Read a paper
    suspend fun read(id: Int): Paper = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_PAPER_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val arxivId = resultSet.getString("arxiv_id")
            val title = resultSet.getString("title")
            val authors = resultSet.getString("authors")
            val abstract = resultSet.getString("abstract")
            val doi = resultSet.getString("doi")
            val updateDate = resultSet.getString("update_date")
            val creationDate = resultSet.getString("creation_date")

            return@withContext Paper(arxivId, title, authors, abstract, doi, updateDate, creationDate)
        } else {
            throw Exception("Record not found")
        }
    }

    // Search paper by Title
    suspend fun search(query: String, category: String): List<Paper> = withContext(Dispatchers.IO) {
        val statement: Statement

        if (category == "title") {
            statement = connection.prepareStatement(SELECT_PAPERS_TITLE_LIKE)
        } else if (category == "authors") {
            statement = connection.prepareStatement(SELECT_PAPERS_AUTHORS_LIKE)
        } else if (category == "abstract") {
            statement = connection.prepareStatement(SELECT_PAPERS_ABSTRACT_LIKE)
        } else {
            throw Exception("Category not found")
        }

        statement.setString(1, "%$query%")
        val resultSet = statement.executeQuery()
        val papers = mutableListOf<Paper>()

        while (resultSet.next()) {
            val arxivId = resultSet.getString("arxiv_id")
            val title = resultSet.getString("title")
            val authors = resultSet.getString("authors")
            val abstract = resultSet.getString("abstract")
            val doi = resultSet.getString("doi")
            val updateDate = resultSet.getString("update_date")
            val creationDate = resultSet.getString("creation_date")
            papers.add(Paper(arxivId, title, authors, abstract, doi, updateDate, creationDate))
        }
        return@withContext papers
    }

    // Update a paper
    suspend fun update(id: Int, paper: Paper) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_PAPER)
        statement.setString(1, paper.update_date)
        statement.setString(2, paper.abstract)
        statement.setInt(3, id)
        statement.executeUpdate()
    }

    // Delete a paper
    suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_PAPER)
        statement.setInt(1, id)
        statement.executeUpdate()
    }
}

