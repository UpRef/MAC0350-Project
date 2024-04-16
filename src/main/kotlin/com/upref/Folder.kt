package com.upref

import com.upref.Paper

class Folder {
    var id: String
    var papers: MutableList<Paper>

    constructor(id: String, papers: MutableList<Paper>) {
        this.id = id
        this.papers = papers
    }

    fun addPaper(paper: Paper) {
        this.papers.add(paper)
    }

    fun removePaper(paper: Paper) {
        // search paper in this.papers, if found remove
        this.papers.remove(paper)
    }
}
