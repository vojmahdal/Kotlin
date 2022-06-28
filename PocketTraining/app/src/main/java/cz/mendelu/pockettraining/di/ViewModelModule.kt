package cz.mendelu.pockettraining.di

import cz.mendelu.pockettraining.ui.exercises.AddExerciseViewModel
import cz.mendelu.pockettraining.ui.exercises.ExerciseListViewModel
import cz.mendelu.pockettraining.ui.home.HomeListViewModel
import cz.mendelu.pockettraining.ui.home.HomeViewModel
import cz.mendelu.pockettraining.ui.plans.ExerciseToPlanViewModel
import cz.mendelu.pockettraining.ui.plans.PlanListViewModel
import cz.mendelu.pockettraining.ui.plans.PlanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
       ExerciseListViewModel(get())
    }
   viewModel{
      AddExerciseViewModel(get())
   }
    viewModel{
        PlanListViewModel(get())
    }
    viewModel {
        PlanViewModel(get())
    }
    viewModel {
        ExerciseToPlanViewModel(get())
    }
    viewModel{
        HomeViewModel(get())
    }
    viewModel{
        HomeListViewModel(get())
    }
}