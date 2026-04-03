package com.example.coachrythmo.data.seed

import com.example.coachrythmo.domain.model.Exercise

object ExerciseSeed {

    fun getExercises(): List<Exercise> {
        return listOf(
            Exercise(name = "Développé couché", category = "Pectoraux"),
            Exercise(name = "Pompes", category = "Pectoraux"),
            Exercise(name = "Tractions", category = "Dos"),
            Exercise(name = "Curl biceps", category = "Dos"),
            Exercise(name = "Squats", category = "Jambes"),
            Exercise(name = "Fentes", category = "Jambes"),
            Exercise(name = "Course à pied", category = "Cardio")
        )
    }

}