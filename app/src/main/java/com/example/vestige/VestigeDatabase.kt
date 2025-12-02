package com.example.vestige.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vestige.CarritoDao
import com.example.vestige.data.CarritoItem

@Database(entities = [CarritoItem::class], version = 1, exportSchema = false)
abstract class VestigeDatabase : RoomDatabase() {

    abstract fun carritoDao(): CarritoDao

    companion object {
        @Volatile
        private var Instance: VestigeDatabase? = null

        fun getDatabase(context: Context): VestigeDatabase {
            // Implementación Singleton: solo permite una instancia de la BD
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, VestigeDatabase::class.java, "vestige_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}