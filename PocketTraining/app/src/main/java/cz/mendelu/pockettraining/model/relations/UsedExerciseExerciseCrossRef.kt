package cz.mendelu.pockettraining.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["usedExerciseName", "exerciseName"])
data class UsedExerciseExerciseCrossRef(
    val usedExerciseName: String,
    val exerciseName: String


)
