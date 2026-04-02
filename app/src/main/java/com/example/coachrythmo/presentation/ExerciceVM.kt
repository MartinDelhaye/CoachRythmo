package com.example.coachrythmo.presentation

import com.example.coachrythmo.domain.model.DifficultyType

data class ExerciceVM(
    val id: Int,
    val name: String,
    val category: String,
    val series: Int,
    val repsMin: Int,
    val repsMax: Int,
    val description: String = ""
)

val exerciceDatabase = listOf(
    // Pectoraux / Triceps
    ExerciceVM(1, "Échauffement", "Pectoraux / Triceps", 1, 0, 0, "Mobilisation articulaire"),
    ExerciceVM(2, "Développé Couché (Barre)", "Pectoraux / Triceps", 5, 4, 15),
    ExerciceVM(3, "Presse Épaules (Haltère)", "Pectoraux / Triceps", 3, 12, 15),
    ExerciceVM(4, "Butterfly (Pec Deck)", "Pectoraux / Triceps", 3, 15, 20),
    ExerciceVM(5, "Élevation Latérale (Haltère)", "Pectoraux / Triceps", 3, 15, 20),
    ExerciceVM(6, "Extensions Triceps Corde", "Pectoraux / Triceps", 3, 15, 20),
    ExerciceVM(7, "Dips Triceps", "Pectoraux / Triceps", 3, 8, 12),
    ExerciceVM(8, "Développé Incliné (Haltère)", "Pectoraux / Triceps", 4, 10, 15),
    ExerciceVM(9, "Écartés Poulie Basse", "Pectoraux / Triceps", 3, 12, 15),
    ExerciceVM(10, "Pushdown Barre Droite", "Pectoraux / Triceps", 3, 12, 15),

    // Dos / Biceps
    ExerciceVM(11, "Échauffement", "Dos / Biceps", 1, 0, 0, "Mobilisation épaules et dos"),
    ExerciceVM(12, "Tractions Pronation", "Dos / Biceps", 4, 6, 10),
    ExerciceVM(13, "Rowing Barre", "Dos / Biceps", 4, 8, 12),
    ExerciceVM(14, "Tirage Poulie Haute", "Dos / Biceps", 3, 10, 15),
    ExerciceVM(15, "Rowing Haltère Unilatéral", "Dos / Biceps", 3, 10, 12),
    ExerciceVM(16, "Curl Biceps Barre", "Dos / Biceps", 3, 10, 15),
    ExerciceVM(17, "Curl Marteau Haltère", "Dos / Biceps", 3, 12, 15),
    ExerciceVM(18, "Face Pull Corde", "Dos / Biceps", 3, 15, 20),
    ExerciceVM(19, "Shrugs Haltère", "Dos / Biceps", 3, 12, 15),
    ExerciceVM(20, "Tirage Horizontal Poulie", "Dos / Biceps", 3, 12, 15),

    // Jambes
    ExerciceVM(21, "Échauffement", "Jambes", 1, 0, 0, "Mobilisation hanches et genoux"),
    ExerciceVM(22, "Squat Barre", "Jambes", 5, 5, 10),
    ExerciceVM(23, "Presse à Cuisses", "Jambes", 4, 10, 15),
    ExerciceVM(24, "Fentes Marchées Haltère", "Jambes", 3, 10, 12),
    ExerciceVM(25, "Leg Curl Couché", "Jambes", 3, 12, 15),
    ExerciceVM(26, "Leg Extension", "Jambes", 3, 15, 20),
    ExerciceVM(27, "Mollets Debout Machine", "Jambes", 4, 15, 20),
    ExerciceVM(28, "Soulevé de Terre Roumain", "Jambes", 4, 8, 12),
    ExerciceVM(29, "Hip Thrust Barre", "Jambes", 4, 10, 15),
    ExerciceVM(30, "Abducteurs Machine", "Jambes", 3, 15, 20),

    // Endurance
    ExerciceVM(31, "Échauffement", "Endurance", 1, 0, 0, "5 min marche rapide"),
    ExerciceVM(32, "Course à Pied", "Endurance", 1, 20, 30),
    ExerciceVM(33, "Vélo Stationnaire", "Endurance", 1, 20, 40),
    ExerciceVM(34, "Rameur", "Endurance", 3, 5, 10),
    ExerciceVM(35, "Corde à Sauter", "Endurance", 5, 2, 3),
    ExerciceVM(36, "Burpees", "Endurance", 4, 10, 15),
    ExerciceVM(37, "Mountain Climbers", "Endurance", 4, 20, 30),
    ExerciceVM(38, "Jumping Jacks", "Endurance", 3, 30, 40),
    ExerciceVM(39, "HIIT Sprint", "Endurance", 8, 20, 30),
    ExerciceVM(40, "Elliptique", "Endurance", 1, 20, 30),

    // Full Body
    ExerciceVM(41, "Échauffement", "Full Body", 1, 0, 0, "Mobilisation générale"),
    ExerciceVM(42, "Deadlift", "Full Body", 4, 5, 8),
    ExerciceVM(43, "Développé Militaire", "Full Body", 4, 8, 12),
    ExerciceVM(44, "Tractions Supination", "Full Body", 3, 6, 10),
    ExerciceVM(45, "Squat Gobelet", "Full Body", 3, 12, 15),
    ExerciceVM(46, "Pompes", "Full Body", 3, 15, 20),
    ExerciceVM(47, "Planche", "Full Body", 3, 30, 60),
    ExerciceVM(48, "Kettlebell Swing", "Full Body", 4, 15, 20),
    ExerciceVM(49, "Clean & Press", "Full Body", 3, 8, 10),
    ExerciceVM(50, "Turkish Get-Up", "Full Body", 3, 5, 8)
)

fun getExercicesByCategory(category: String): List<ExerciceVM> {
    return exerciceDatabase.filter { it.category == category }
}