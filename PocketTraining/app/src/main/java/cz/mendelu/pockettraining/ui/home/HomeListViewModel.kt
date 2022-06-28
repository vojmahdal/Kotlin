package cz.mendelu.pockettraining.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.pockettraining.database.IExercisesLocalRepository
import cz.mendelu.pockettraining.model.Plan
import cz.mendelu.pockettraining.model.UsedExercise

class HomeListViewModel(private val repository: IExercisesLocalRepository) : ViewModel() {

    var id: Long? = null
    var plan: Plan = Plan("")

    fun getUsedExercise(): LiveData<MutableList<UsedExercise>> {
        return repository.getUsedExercise(id!!)
    }
    suspend fun findPlanById(): Plan = repository.findPlanById(id!!)


}