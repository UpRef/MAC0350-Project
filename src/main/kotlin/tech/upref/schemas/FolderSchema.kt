package tech.upref.schemas

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.Statement

@Serializable
data class Folder(val name: String, val user_id: Int)
class FolderService(private val connection: Connection) {
    companion object {
        private const val CREATE_TABLE_FOLDERS =
            "CREATE TABLE FOLDERS (ID SERIAL PRIMARY KEY, NAME TEXT, USER_ID TEXT);"
        private const val SELECT_FOLDER_BY_ID = "SELECT name, user_id FROM folders WHERE id = ?"
        private const val INSERT_FOLDER = "INSERT INTO folders (name, user_id) VALUES (?, ?)"
        private const val UPDATE_FOLDER = "UPDATE folders SET name = ? WHERE id = ?"
        private const val DELETE_FOLDER = "DELETE FROM folders WHERE id = ?"
    }

    init {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_FOLDERS)
    }

    private var newFolderId = 0

    // Create new folder
    suspend fun create(folder: Folder): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_FOLDER, Statement.RETURN_GENERATED_KEYS)

        statement.setString(1, folder.name)
        statement.setInt(2, folder.user_id)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw Exception("Unable to retrieve the id of the newly inserted folder")
        }
    }

    // Read a folder
    suspend fun read(id: Int): Folder = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_FOLDER_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val name = resultSet.getString("name")
            val userId = resultSet.getInt("user_id")

            return@withContext Folder(name, userId)
        } else {
            throw Exception("Record not found")
        }
    }

    // Update a folder
    suspend fun update(id: Int, folder: Folder) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_FOLDER)
        statement.setString(1, folder.name)
        statement.setInt(3, id)
        statement.executeUpdate()
    }

    // Delete a folder
    suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_FOLDER)
        statement.setInt(1, id)
        statement.executeUpdate()
    }
}

