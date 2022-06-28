package cz.mendelu.pockettraining.ui.exercises

import androidx.lifecycle.ViewModel
import cz.mendelu.pockettraining.database.IExercisesLocalRepository
import cz.mendelu.pockettraining.model.Exercise

class AddExerciseViewModel(private val repository: IExercisesLocalRepository) : ViewModel() {

    var id: Long? = null
    var exercise: Exercise = Exercise("")

    suspend fun saveExercise(){
        if(id == null){
            repository.insertExercise(exercise)
        } else{
            repository.updateExercise(exercise)
        }
    }
    suspend fun findExerciseById(): Exercise = repository.findById(id!!)

    suspend fun deleteExercise(){
        repository.deleteExercise(exercise)
    }
}