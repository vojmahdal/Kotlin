package cz.mendelu.pockettraining.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import cz.mendelu.pockettraining.model.Exercise
import cz.mendelu.pockettraining.model.UsedExercise

data class ExerciseWithUsedExercise(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exerciseName",
        entityColumn = "usedExerciseName",
        associateBy = Junction(UsedExerciseExerciseCrossRef::class)
    )
    val usedExercises: List<UsedExercise>

)