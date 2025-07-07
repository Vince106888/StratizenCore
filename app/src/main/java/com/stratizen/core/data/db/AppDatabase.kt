package com.stratizen.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stratizen.core.data.model.Event
import com.stratizen.core.data.model.Xp

/**
 * Room database configuration for Stratizen Core.
 *
 * - Contains entities: Event and Xp.
 * - Provides DAOs: EventDao and XpDao.
 * - Singleton pattern ensures only one instance of the DB exists.
 */
@Database(
    entities = [Event::class, Xp::class], // Define database tables as entities
    version = 3,                          // Increment this to trigger migrations
    exportSchema = false                 // Prevent Room from exporting schema into a folder
)
abstract class AppDatabase : RoomDatabase() {

    // Abstract function to access EventDao (Data Access Object for Event operations)
    abstract fun eventDao(): EventDao

    // Abstract function to access XpDao (Data Access Object for XP operations)
    abstract fun xpDao(): XpDao

    companion object {
        // Volatile ensures visibility of changes to INSTANCE across threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Returns a singleton instance of the database.
         * Uses synchronized block to ensure thread safety.
         */
        fun getDatabase(context: Context): AppDatabase {
            // Return existing instance or build a new one in a thread-safe way
            return INSTANCE ?: synchronized(this) {
                // Create database instance using Room
                val instance = Room.databaseBuilder(
                    context.applicationContext,       // Use application context to avoid leaks
                    AppDatabase::class.java,          // Reference to this database class
                    "stratizen_core_db"               // Database name
                )
                    .fallbackToDestructiveMigration()     // Auto-destroys & recreates DB on schema change (dev use)
                    .build()

                INSTANCE = instance // Cache the instance for future use
                instance
            }
        }
    }
}
