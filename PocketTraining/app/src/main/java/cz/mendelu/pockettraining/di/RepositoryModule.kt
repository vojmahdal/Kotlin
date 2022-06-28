package cz.mendelu.pockettraining.di

import cz.mendelu.pockettraining.database.ExercisesDao
import cz.mendelu.pockettraining.database.ExercisesLocalRepositoryImpl
import cz.mendelu.pockettraining.database.IExercisesLocalRepository
import org.koin.dsl.module


val repositoryModule = module {
    single { provideLocalExercisesRepository(get()) }
}
fun provideLocalExercisesRepository(dao: ExercisesDao): IExercisesLocalRepository = ExercisesLocalRepositoryImpl(dao)
