package com.example.coachrythmo.data.repository

import com.example.coachrythmo.data.remote.RetrofitInstance
import com.example.coachrythmo.data.source.ExerciseDao
import com.example.coachrythmo.domain.model.Exercise

object ExerciseRepository {

    suspend fun fetchAndStore(exerciseDao: ExerciseDao) {
        val response = RetrofitInstance.api.getExercises(limit = 100)

        val exercises = response.results.mapNotNull { dto ->
            val name = dto.translations.firstOrNull { it.language == 12 }?.name  // français
                ?: dto.translations.firstOrNull { it.language == 2 }?.name       // anglais fallback
                ?: return@mapNotNull null

            if (name.isBlank() || dto.category == null) return@mapNotNull null

            Exercise(
                id = dto.id,
                name = name.trim(),
                category = dto.category.name
            )
        }.distinctBy { it.name }

        if (exercises.isNotEmpty()) {
            exerciseDao.insertAll(exercises)
        }
    }
}