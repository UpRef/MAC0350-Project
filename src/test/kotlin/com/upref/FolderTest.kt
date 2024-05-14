package com.upref

import com.upref.models.Folder
import com.upref.models.Paper

//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test

class FolderTest {
    fun testAddPaper() {
        val empty = mutableListOf<Paper>()
        val paper = Paper(
            42,
            "Title",
            "author A, author B",
            "abstract",
            "keyword1, keyword2",
            "link1, link2",
        )
        val folder = Folder(52, "title")
        //assert blabla
    }

    @org.junit.jupiter.api.Test
    fun getId() {
    }

    @org.junit.jupiter.api.Test
    fun setId() {
    }

    @org.junit.jupiter.api.Test
    fun getPapers() {
    }

    @org.junit.jupiter.api.Test
    fun setPapers() {
    }

    @org.junit.jupiter.api.Test
    fun addPaper() {
    }

    @org.junit.jupiter.api.Test
    fun removePaper() {
    }
}