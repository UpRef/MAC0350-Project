package tech.upref.schemas

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.Statement

@Serializable
data class PaperFolder(val paper_id: Int, val folder_id: Int)
class PaperFolderService(private val connection: Connection) {
    companion object {
        private const val CREATE_TABLE_PAPERFOLDERS =
            "CREATE TABLE PAPERFOLDERS (PAPER_ID INT NOT NULL, FOLDER_ID INT NOT NULL," +
                    "PRIMARY KEY (PAPER_ID, FOLDER_ID)," +
                    "FOREIGN KEY (FOLDER_ID) REFERENCES folders (ID) ON DELETE CASCADE," +
                    "FOREIGN KEY (PAPER_ID) REFERENCES papers (ID) ON DELETE CASCADE);"
        private const val SELECT_PAPERS_IDS_FROM_FOLDER = "SELECT paper_id FROM paperfolders WHERE folder_id = ?"
        private const val SELECT_PAPERS_FROM_FOLDER = "SELECT arxiv_id, title, authors, abstract, doi, update_date, creation_date, paper_id, folder_id " +
                "FROM papers, paperfolders WHERE folder_id = ?"
        //private const val SELECT_PAPERS_FROM_FOLDER = "SELECT paper_id FROM paperfolders WHERE folder_id = ?"
        private const val INSERT_PAPER_INTO_FOLDER = "INSERT INTO paperfolders (paper_id, folder_id) VALUES (?, ?)"
        private const val DELETE_PAPER_FROM_FOLDER = "DELETE FROM paperfolders WHERE (paper_id = ? AND folder_id = ?)"
    }

    init {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_PAPERFOLDERS)
    }

    private var newPaperFolderId = 0

    // Insert paper in Folder
    suspend fun create(paperfolder: PaperFolder): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_PAPER_INTO_FOLDER, Statement.RETURN_GENERATED_KEYS)

        statement.setInt(1, paperfolder.paper_id)
        statement.setInt(2, paperfolder.folder_id)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw Exception("Unable to retrieve the id of the newly inserted paperfolder")
        }
    }

    // Get a list of papers from a folder
    suspend fun read(folder_id: Int): List<Paper> = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_PAPERS_FROM_FOLDER)
        statement.setInt(1, folder_id)
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

    // Get a list of papers ids from a folder
    suspend fun read_ids(folder_id: Int): List<Int> = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_PAPERS_IDS_FROM_FOLDER)
        statement.setInt(1, folder_id)
        val resultSet = statement.executeQuery()
        val papers = mutableListOf<Int>()

        while (resultSet.next()) {
            val paperId = resultSet.getInt("paper_id")
            papers.add(paperId)
        }
        return@withContext papers
    }
    // Delete a paper from a folder
    suspend fun delete(paper_id: Int, folder_id: Int) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_PAPER_FROM_FOLDER)
        statement.setInt(1, paper_id)
        statement.setInt(2, folder_id)
        statement.executeUpdate()
    }
}

