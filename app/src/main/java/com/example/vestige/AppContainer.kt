package com.example.vestige

import android.content.Context
import com.example.vestige.data.CarritoRepository
import com.example.vestige.data.OfflineCarritoRepository
import com.example.vestige.data.VestigeDatabase

interface AppContainer {
    val carritoRepository: CarritoRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val carritoRepository: CarritoRepository by lazy {
        val carritoDao = VestigeDatabase.getDatabase(context).carritoDao()
        OfflineCarritoRepository(carritoDao)
    }
}