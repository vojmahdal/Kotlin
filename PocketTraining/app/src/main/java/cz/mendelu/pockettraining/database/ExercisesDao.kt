package cz.mendelu.pockettraining.database

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.mendelu.pockettraining.model.Exercise
import cz.mendelu.pockettraining.model.Plan
import cz.mendelu.pockettraining.model.UsedExercise
import cz.mendelu.pockettraining.model.relations.ExerciseWithUsedExercise
import cz.mendelu.pockettraining.model.relations.PlanWithUsedExercise
import cz.mendelu.pockettraining.model.relations.UsedExerciseExerciseCrossRef
import cz.mendelu.pockettraining.model.relations.UsedExerciseWithExercise


@Dao
interface ExercisesDao {

    @Query("SELECT * FROM exercises")
    fun getAll(): LiveData<MutableList<Exercise>>

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun findById(id: Long): Exercise

    @Insert
    suspend fun insertExercise(exercise: Exercise): Long

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    //////

    @Query("SELECT * FROM plans")
    fun getPlanAll(): LiveData<MutableList<Plan>>

    @Query("SELECT * FROM plans WHERE id = :id")
    suspend fun findPlanById(id: Long): Plan

    @Insert
    suspend fun insertPlan(plan: Plan): Long

    @Update
    suspend fun updatePlan(plan: Plan)

    @Delete
    suspend fun deletePlan(plan: Plan)







    ///try this to show exercises in plan fragment
    @Transaction
    @Query ("SELECT * FROM used_exercise WHERE fromPlanId = :planId")
    fun getUsedExercise(planId: Long): LiveData<MutableList<UsedExercise>>


    //not working need to remove this
    //for show in PlanFragment
    @Transaction
    @Query("SELECT * FROM plans WHERE id = :planId")
    fun getPlanWithUsedExercise(planId: Long): LiveData<MutableList<PlanWithUsedExercise>>
    //maybe implement to list used_exercise
    //


    @Insert
    suspend fun insertStudentSubjectCrossRef(crossRef: UsedExerciseExerciseCrossRef)

    ///
    @Transaction
    @Query("SELECT * FROM exercises WHERE exerciseName = :exerciseName")
    fun getUsedExercisesOfExercises(exerciseName: String): LiveData<MutableList<ExerciseWithUsedExercise>>

    //for show actual modify exercise from plan
    @Transaction
    @Query("SELECT * FROM used_exercise WHERE usedExerciseName = :usedExercise")
    fun getExercisesOfUsedExercises(usedExercise: String): List<UsedExerciseWithExercise>


    //for editing exercise to add plans
    @Insert
    suspend fun insertUsedExercise(usedExercise: UsedExercise) : Long

    @Delete
    suspend fun deleteUsedExercise(usedExercise: UsedExercise)

    //for remove used exercises with plan
    @Query("DELETE FROM used_exercise WHERE fromPlanId = :id")
    suspend fun deletePlanWithExercises(id: Long)

    @Query("SELECT * FROM used_exercise WHERE id = :id")
    suspend fun findUsedExerciseById(id: Long): UsedExercise

    @Update
    suspend fun updateUsedExercise(usedExercise: UsedExercise)
}