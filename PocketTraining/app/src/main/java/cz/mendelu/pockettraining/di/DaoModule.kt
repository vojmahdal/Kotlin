package cz.mendelu.pockettraining.di

import cz.mendelu.pockettraining.database.ExercisesDao
import cz.mendelu.pockettraining.database.ExercisesDatabase
import org.koin.dsl.module

val daoModule = module {
    single {
        provideExercisesDao(get())
    }
}

fun provideExercisesDao(database: ExercisesDatabase): ExercisesDao = database.exercisesDao()


