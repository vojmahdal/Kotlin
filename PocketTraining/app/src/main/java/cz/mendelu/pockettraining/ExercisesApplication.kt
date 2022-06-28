package cz.mendelu.pockettraining

import android.app.Application
import cz.mendelu.pockettraining.di.daoModule
import cz.mendelu.pockettraining.di.databaseModule
import cz.mendelu.pockettraining.di.repositoryModule
import cz.mendelu.pockettraining.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ExercisesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                databaseModule,
                daoModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}