package com.example.vestige.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vestige.Producto

@Entity(tableName = "carrito_items")
data class CarritoItem(
    @PrimaryKey
    val productoId: Int,
    val imagenUrl: String,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val cantidad: Int = 1
)


fun Producto.toCarritoItem(): CarritoItem {
    return CarritoItem(
        productoId = this.id,
        imagenUrl = this.imagenUrl,
        nombre = this.nombre,
        precio = this.precio,
        descripcion = this.descripcion,
        cantidad = 1
    )
}