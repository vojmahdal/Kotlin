package cz.mendelu.pockettraining.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import cz.mendelu.pockettraining.model.Plan
import cz.mendelu.pockettraining.model.UsedExercise

data class PlanWithUsedExercise (
    @Embedded val plan: Plan,
    @Relation(
        parentColumn = "id",
        entityColumn = "fromPlanId"
    )
    val usedExercises: MutableList<UsedExercise>
        )