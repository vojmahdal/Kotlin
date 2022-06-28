package cz.mendelu.pockettraining.ui.plans

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.pockettraining.database.IExercisesLocalRepository
import cz.mendelu.pockettraining.model.Plan
import cz.mendelu.pockettraining.model.UsedExercise
import cz.mendelu.pockettraining.model.relations.PlanWithUsedExercise
import cz.mendelu.pockettraining.model.relations.UsedExerciseWithExercise

class PlanViewModel(private val repository: IExercisesLocalRepository) : ViewModel() {
    var id: Long? = null
    var plan: Plan = Plan("")

    suspend fun savePlan(){
        if(id == null){
            repository.insertPlan(plan)
        }else{
            repository.updatePlan(plan)
        }
    }
    suspend fun findPlanById(): Plan = repository.findPlanById(id!!)

    fun getUsedExercise(): LiveData<MutableList<UsedExercise>>{
        return repository.getUsedExercise(id!!)
    }

    suspend fun deletePlan(){
        return repository.deletePlan(plan)
    }

    suspend fun deletePlanWithExercises(){
        return repository.deletePlanWithExercises(id!!)
    }


}