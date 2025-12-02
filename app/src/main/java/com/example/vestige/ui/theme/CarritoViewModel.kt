package com.example.vestige.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vestige.data.CarritoItem
import com.example.vestige.data.CarritoRepository
import com.example.vestige.data.toCarritoItem
import com.example.vestige.Producto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import com.example.vestige.data.VestigeDatabase



data class CarritoUiState(
    val listaProductos: List<CarritoItem> = emptyList(),
    val totalArticulos: Int = 0,
    val isLoading: Boolean = false
)


class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {


    val uiState: StateFlow<CarritoUiState> =
        repository.obtenerTodos()
            .map { items ->
                CarritoUiState(
                    listaProductos = items,
                    totalArticulos = items.sumOf { it.cantidad }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CarritoUiState()
            )

    fun agregarProductoAlCarrito(producto: Producto) {
        viewModelScope.launch {
            val existingItem = uiState.value.listaProductos.find { it.productoId == producto.id }

            if (existingItem != null) {
                val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
                repository.insertar(updatedItem)
            } else {
                repository.insertar(producto.toCarritoItem())
            }
        }
    }

    fun eliminarItem(item: CarritoItem) {
        viewModelScope.launch {
            repository.eliminar(item)
        }
    }
}

object CarritoViewModelProvider {
    fun Factory(repository: CarritoRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return CarritoViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}