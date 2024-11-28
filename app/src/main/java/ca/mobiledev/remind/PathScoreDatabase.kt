package ca.mobiledev.remind

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [PathScore::class], version = 1, exportSchema = false)
abstract class PathScoreDatabase : RoomDatabase() {
    abstract fun PathScoreDao(): PathScoreDao?

    companion object {
        @Volatile
        private var INSTANCE: PathScoreDatabase? = null
        private const val NUMBER_OF_THREADS = 4

        // Define an ExecutorService with a fixed thread pool which is used to run database operations asynchronously on a background thread
        val databaseWriterExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        // Singleton access to the database
        fun getDatabase(context: Context): PathScoreDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    PathScoreDatabase::class.java, "pathscore_database")
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
