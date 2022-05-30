package com.google.samples.apps.nowinandroid.core.model.data

/**
 * External data layer representation of an NiA Author
 */
data class Author(
    val id: String,
    val name: String,
    val imageUrl: String,
    val twitter: String,
    val mediumPage: String,
    val bio: String,
)
