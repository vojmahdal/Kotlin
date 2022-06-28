package cz.mendelu.pockettraining.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.pockettraining.database.IExercisesLocalRepository
import cz.mendelu.pockettraining.model.Plan

class HomeViewModel(private val repository: IExercisesLocalRepository) : ViewModel() {


    fun getPlanAll(): LiveData<MutableList<Plan>>{
        return repository.getPlanAll()
    }
}