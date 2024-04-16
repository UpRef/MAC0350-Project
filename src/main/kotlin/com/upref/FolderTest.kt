package com.upref

import org.jetbrains.kotlin.test.junit.*
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test

class FolderTest {
    fun testAddPaper() {
        val empty = mutableListOf<Paper>()
        val paper = Paper(
            "paper1",
            "title",
            mutableListOf<String>(),
            "abstract",
            mutableListOf<String>(),
            mutableListOf<String>(),
        )
        val folder = Folder("folder1", empty)
        folder.addPaper(paper)
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