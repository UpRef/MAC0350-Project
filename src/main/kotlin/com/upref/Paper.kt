package com.upref

data class Paper(
    var id: String,
    var title: String,
    var authors: MutableList<String>,
    var abstract: String,
    var keywords: MutableList<String>,
    var links: MutableList<String>
)