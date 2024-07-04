package tech.upref.schemas

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.Statement

@Serializable
data class User(val name: String, val email: String, val password: String)
class UserService(private val connection: Connection) {
    companion object {
        private const val CREATE_TABLE_USERS =
            "CREATE TABLE USERS (ID SERIAL PRIMARY KEY, NAME TEXT, EMAIL TEXT, PASSWORD TEXT);"
        private const val SELECT_USER_BY_ID = "SELECT name, email, password FROM users WHERE id = ?"
        private const val INSERT_USER = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)"
        private const val UPDATE_USER = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?"
        private const val DELETE_USER = "DELETE FROM users WHERE id = ?"
    }

    init {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_USERS)
    }

    private var newUserId = 0

    // Create new user
    suspend fun create(user: User): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)

        statement.setString(1, user.name)
        statement.setString(2, user.email)
        statement.setString(3, user.password)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw Exception("Unable to retrieve the id of the newly inserted user")
        }
    }

    // Read a user
    suspend fun read(id: Int): User = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_USER_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val name = resultSet.getString("name")
            val email = resultSet.getString("email")
            val password = resultSet.getString("password")

            return@withContext User(name, email, password)
        } else {
            throw Exception("Record not found")
        }
    }

    // Update a user
    suspend fun update(id: Int, user: User) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_USER)
        statement.setString(1, user.name)
        statement.setString(2, user.email)
        statement.setString(3, user.password)
        statement.setInt(4, id)
        statement.executeUpdate()
    }

    // Delete a user
    suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_USER)
        statement.setInt(1, id)
        statement.executeUpdate()
    }
}
