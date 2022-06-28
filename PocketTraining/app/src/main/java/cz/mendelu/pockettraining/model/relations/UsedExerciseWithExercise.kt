package cz.mendelu.pockettraining.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import cz.mendelu.pockettraining.model.Exercise
import cz.mendelu.pockettraining.model.UsedExercise

data class UsedExerciseWithExercise(
    @Embedded val usedExercise: UsedExercise,
    @Relation(
        parentColumn = "usedExerciseName",
        entityColumn = "exerciseName",
        associateBy = Junction(UsedExerciseExerciseCrossRef::class)
    )
    val exercises: List<Exercise>

)
