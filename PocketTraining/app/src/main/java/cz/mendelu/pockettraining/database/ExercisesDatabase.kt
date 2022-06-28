package cz.mendelu.pockettraining.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.mendelu.pockettraining.model.Exercise
import cz.mendelu.pockettraining.model.Plan
import cz.mendelu.pockettraining.model.UsedExercise
import cz.mendelu.pockettraining.model.relations.UsedExerciseExerciseCrossRef


@Database(
    entities = [
        Exercise::class,
        Plan::class,
        UsedExercise::class,
        UsedExerciseExerciseCrossRef::class
               ],
    version = 5, exportSchema = true)
abstract class ExercisesDatabase : RoomDatabase() {
    abstract fun exercisesDao(): ExercisesDao

    companion object{
        @Volatile
        private var INSTANCE: ExercisesDatabase? = null
        fun getDatabase(context: Context): ExercisesDatabase{
            if(INSTANCE == null){
                synchronized(ExercisesDatabase::class.java){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ExercisesDatabase::class.java,
                            "training_database"
                        ).fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE!!
        }


        private val MIGRATION_1_2: Migration = object : Migration(1,2){

            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE exercises ADD COLUMN 'description' TEXT")
                database.execSQL("ALTER TABLE plans ADD COLUMN 'name' TEXT")
            }
        }
    }
}