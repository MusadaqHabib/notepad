package com.example.notepad

import java.util.*

data class note(
    val id: Int,
    var title: String,
    var content: String,
    val date: Date = Date()
)

