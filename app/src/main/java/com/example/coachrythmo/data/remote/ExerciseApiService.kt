package com.example.coachrythmo.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApiService {

    @GET("api/v2/exerciseinfo/?format=json&language=1")
    suspend fun getExercises(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): WgerExerciseListResponse
}