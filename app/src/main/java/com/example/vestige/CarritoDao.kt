package com.example.vestige

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.vestige.data.CarritoItem

@Dao
interface CarritoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(item: CarritoItem)

    @Delete
    suspend fun eliminar(item: CarritoItem)


    @Query("SELECT * FROM carrito_items ORDER BY nombre ASC")
    fun obtenerTodos(): Flow<List<CarritoItem>>


    @Query("UPDATE carrito_items SET cantidad = :nuevaCantidad WHERE productoId = :id")
    suspend fun actualizarCantidad(id: Int, nuevaCantidad: Int)
}