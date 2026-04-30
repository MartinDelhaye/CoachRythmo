package com.example.coachrythmo.data.remote

data class WgerExerciseListResponse(
    val results: List<WgerExercise>
)

data class WgerExercise(
    val id: Int,
    val category: WgerCategory?,
    val translations: List<WgerTranslation>
)

data class WgerCategory(
    val name: String
)

data class WgerTranslation(
    val name: String,
    val language: Int
)