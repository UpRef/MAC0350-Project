package com.upref

import com.upref.Paper

data class Folder(
    var id: String,
    var papers: MutableList<Paper>
)