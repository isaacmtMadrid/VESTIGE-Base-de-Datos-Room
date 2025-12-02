package com.example.vestige

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vestige.CarritoManager
import com.example.vestige.Producto
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.vestige.ui.CarritoViewModel
import com.example.vestige.data.CarritoItem
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito(
    navController: NavController,
    viewModel: CarritoViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val carritoItems = uiState.listaProductos
    val totalArticulos = uiState.totalArticulos
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tu Carrito ($totalArticulos)") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (carritoItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Tu carrito está vacío.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 8.dp)
                ) {
                    items(carritoItems, key = { it.productoId }) { item ->
                        ItemCarrito(
                            item = item,
                            onRemove = { itemToRemove ->
                                viewModel.eliminarItem(itemToRemove)
                            }
                        )
                        Divider()
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 8.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        val totalPrecio = carritoItems.sumOf { it.precio * it.cantidad }
                        Text(text = "Total estimado: ${totalPrecio.toCurrencyString()}", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {  },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Proceder a Pagar")
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ItemCarrito(item: CarritoItem, onRemove: (CarritoItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.imagenUrl,
            contentDescription = item.nombre,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.nombre, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text("Precio: ${item.precio.toCurrencyString()}", color = Color.Gray)
            Text("Cantidad: ${item.cantidad}", color = Color.DarkGray)
        }

        IconButton(
            onClick = { onRemove(item) }
        ) {
            Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.Red)
        }
    }
}