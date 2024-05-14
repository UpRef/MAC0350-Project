package com.upref.models
import org.jetbrains.exposed.sql.*

data class RelationPaperFolder  (var paper: Paper, var folder: Folder)

object RelationsPaperFolder : Table() {
    val paper_id = integer("paper_id") // reference("id", Papers)
    val folder_id = integer("folder_id") // reference("id", Folders)
    override val primaryKey = PrimaryKey(paper_id, folder_id, name = "PK_PaperFolder")
}