package com.example.vestige.data

import com.example.vestige.CarritoDao
import kotlinx.coroutines.flow.Flow

interface CarritoRepository {
    fun obtenerTodos(): Flow<List<CarritoItem>>
    suspend fun insertar(item: CarritoItem)
    suspend fun eliminar(item: CarritoItem)
}

class OfflineCarritoRepository(private val carritoDao: CarritoDao) : CarritoRepository {
    override fun obtenerTodos(): Flow<List<CarritoItem>> = carritoDao.obtenerTodos()

    override suspend fun insertar(item: CarritoItem) = carritoDao.insertar(item)

    override suspend fun eliminar(item: CarritoItem) = carritoDao.eliminar(item)
}