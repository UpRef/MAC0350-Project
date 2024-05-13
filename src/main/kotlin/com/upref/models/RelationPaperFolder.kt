package com.upref.models

data class RelationPaperFolder  (
    var paper =
)

object RelationsPaperFolder : Table{
    val paper = reference("paper", Papers)
    val folder = reference("folder", Folders)
    override val primaryKey = PrimaryKey(paper, folder, name = "PK_PaperFolder")
}