package cz.mendelu.pockettraining.database

import androidx.lifecycle.LiveData
import cz.mendelu.pockettraining.model.Exercise
import cz.mendelu.pockettraining.model.Plan
import cz.mendelu.pockettraining.model.UsedExercise
import cz.mendelu.pockettraining.model.relations.ExerciseWithUsedExercise
import cz.mendelu.pockettraining.model.relations.PlanWithUsedExercise
import cz.mendelu.pockettraining.model.relations.UsedExerciseWithExercise

class ExercisesLocalRepositoryImpl(private val exercisesDao: ExercisesDao) : IExercisesLocalRepository {
    override fun getAll(): LiveData<MutableList<Exercise>>{
        return exercisesDao.getAll()
    }

    override suspend fun findById(id: Long): Exercise {
        return exercisesDao.findById(id)
    }

    override suspend fun insertExercise(exercise: Exercise): Long {
        return exercisesDao.insertExercise(exercise)
    }

    override suspend fun updateExercise(exercise: Exercise) {
        exercisesDao.updateExercise(exercise)
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        exercisesDao.deleteExercise(exercise)
    }


    //
    override fun getPlanAll(): LiveData<MutableList<Plan>> {
        return exercisesDao.getPlanAll()
    }

    override suspend fun findPlanById(id: Long): Plan {
        return exercisesDao.findPlanById(id)
    }

    override suspend fun insertPlan(plan: Plan): Long {
        return exercisesDao.insertPlan(plan)
    }

    override suspend fun updatePlan(plan: Plan) {
        exercisesDao.updatePlan(plan)
    }

    override suspend fun deletePlan(plan: Plan) {
        exercisesDao.deletePlan(plan)
    }

   override fun getUsedExercise(planId: Long): LiveData<MutableList<UsedExercise>>{
       return exercisesDao.getUsedExercise(planId)
   }

    //for planFragment
    override fun getPlanWithUsedExercise(planId: Long) : LiveData<MutableList<PlanWithUsedExercise>>{
        return exercisesDao.getPlanWithUsedExercise(planId)
    }

    //for later use
   /* override fun getUsedExercisesOfExercises(exerciseName: String) : LiveData<MutableList<ExerciseWithUsedExercise>>{
        return exercisesDao.getUsedExercisesOfExercises(exerciseName)
    }*/

    override suspend fun insertUsedExercise(usedExercise: UsedExercise) : Long{
        return exercisesDao.insertUsedExercise(usedExercise)
    }
    override suspend fun deleteUsedExercise(usedExercise: UsedExercise){
        exercisesDao.deleteUsedExercise(usedExercise)
    }
    //
    override suspend fun deletePlanWithExercises(id: Long){
        exercisesDao.deletePlanWithExercises(id)
    }


    override suspend fun findUsedExerciseById(id: Long): UsedExercise{
       return exercisesDao.findUsedExerciseById(id)
    }
    override suspend fun updateUsedExercise(usedExercise: UsedExercise){
        exercisesDao.updateUsedExercise(usedExercise)
    }
}