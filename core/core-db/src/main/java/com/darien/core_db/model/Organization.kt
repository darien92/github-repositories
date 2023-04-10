package com.darien.core_db.model

import java.util.*

data class Organization(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val timestamp: Long = System.currentTimeMillis()
)
