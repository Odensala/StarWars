package com.odensala.starwars.model

data class FilmsResponse(
    val count: Int,
    val next: Any?,
    val previous: Any?,
    val results: List<Result>
)