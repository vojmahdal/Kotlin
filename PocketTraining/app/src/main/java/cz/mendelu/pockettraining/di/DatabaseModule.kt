package cz.mendelu.pockettraining.di

import android.content.Context
import cz.mendelu.pockettraining.database.ExercisesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single{
        provideDatabase(androidContext())
    }
}

fun provideDatabase(context: Context): ExercisesDatabase = ExercisesDatabase.getDatabase(context)