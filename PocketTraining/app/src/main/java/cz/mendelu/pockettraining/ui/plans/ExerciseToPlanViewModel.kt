package cz.mendelu.pockettraining.ui.plans

import androidx.lifecycle.ViewModel
import cz.mendelu.pockettraining.database.IExercisesLocalRepository
import cz.mendelu.pockettraining.model.Exercise
import cz.mendelu.pockettraining.model.UsedExercise

class ExerciseToPlanViewModel(private val repository: IExercisesLocalRepository) : ViewModel(){
    var id: Long? = null
    var exercise: UsedExercise = UsedExercise("")
    var exerciseName: String? = null
    var planId: Long? = null
    suspend fun findUsedExerciseById(): UsedExercise = repository.findUsedExerciseById(id!!)

    suspend fun saveExercise(){
        if(id == null){
            repository.insertUsedExercise(exercise)
        } else{
            repository.updateUsedExercise(exercise)
        }
    }
    suspend fun deleteUsedExercise(){
        repository.deleteUsedExercise(exercise)
    }

}