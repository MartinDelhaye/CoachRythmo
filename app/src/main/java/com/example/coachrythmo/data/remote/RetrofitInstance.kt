package com.example.coachrythmo.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ExerciseApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://wger.de/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseApiService::class.java)
    }
}