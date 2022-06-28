package cz.mendelu.pockettraining.database

import androidx.lifecycle.LiveData
import androidx.room.Query
import cz.mendelu.pockettraining.model.Exercise
import cz.mendelu.pockettraining.model.Plan
import cz.mendelu.pockettraining.model.UsedExercise
import cz.mendelu.pockettraining.model.relations.ExerciseWithUsedExercise
import cz.mendelu.pockettraining.model.relations.PlanWithUsedExercise

interface IExercisesLocalRepository {
    fun getAll(): LiveData<MutableList<Exercise>>
    suspend fun findById(id: Long): Exercise
    suspend fun insertExercise(exercise: Exercise): Long
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExercise(exercise: Exercise)


    //
    fun getPlanAll(): LiveData<MutableList<Plan>>
    suspend fun findPlanById(id: Long): Plan
    suspend fun insertPlan(plan: Plan): Long
    suspend fun updatePlan(plan: Plan)
    suspend fun deletePlan(plan: Plan)

    //
    fun getUsedExercise(planId: Long): LiveData<MutableList<UsedExercise>>

     fun getPlanWithUsedExercise(planId: Long): LiveData<MutableList<PlanWithUsedExercise>>
    //suspend fun getUsedExercisesOfExercises(exerciseName: String): LiveData<MutableList<ExerciseWithUsedExercise>>

    suspend fun insertUsedExercise(usedExercise: UsedExercise): Long

    suspend fun deleteUsedExercise(usedExercise: UsedExercise)

    suspend fun deletePlanWithExercises(id: Long)


    suspend fun findUsedExerciseById(id: Long): UsedExercise
    suspend fun updateUsedExercise(usedExercise: UsedExercise)
}