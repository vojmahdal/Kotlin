package cz.mendelu.pockettraining.ui.plans

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.pockettraining.database.IExercisesLocalRepository
import cz.mendelu.pockettraining.model.Exercise
import cz.mendelu.pockettraining.model.Plan

class PlanListViewModel(private val repository: IExercisesLocalRepository) : ViewModel() {
    fun getPlanAll(): LiveData<MutableList<Plan>>{
        return repository.getPlanAll()
    }
    fun getAll(): LiveData<MutableList<Exercise>>{
        return repository.getAll()
    }

}