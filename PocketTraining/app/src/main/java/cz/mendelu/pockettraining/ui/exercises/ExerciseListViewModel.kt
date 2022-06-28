package cz.mendelu.pockettraining.ui.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.pockettraining.database.IExercisesLocalRepository
import cz.mendelu.pockettraining.model.Exercise

class ExerciseListViewModel(private val repository: IExercisesLocalRepository): ViewModel() {
    fun getAll(): LiveData<MutableList<Exercise>>{
        return repository.getAll()
    }

}