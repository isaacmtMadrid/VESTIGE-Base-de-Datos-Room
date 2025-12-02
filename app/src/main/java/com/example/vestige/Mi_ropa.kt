package com.example.vestige
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.example.vestige.ui.CarritoViewModel
import com.example.vestige.CarritoManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vestige.ui.theme.VestigeTheme
import com.example.vestige.ui.theme.rsmFamily
import kotlinx.coroutines.launch
import coil.compose.AsyncImage
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object CarritoManager {

    val itemsEnCarrito: SnapshotStateList<Producto> = mutableStateListOf()

    fun agregarProducto(producto: Producto) {
        itemsEnCarrito.add(producto)
    }

    fun eliminarProducto(producto: Producto) {
        itemsEnCarrito.remove(producto)
    }
}
data class Producto(
    val id: Int,
    val imagenUrl: String,
    val nombre: String,
    val precio: Double,
    val descripcion: String
)
val productosDeEjemplo = listOf(
    Producto(1, "https://i.pinimg.com/1200x/a1/f6/53/a1f65344fa43787f2c76489652415d21.jpg", "Vestido de Verano (Floral)", 599.00,descripcion = "Abrigo largo y elegante hecho de una suave mezcla de lana y cachemira. Ideal para los días fríos de invierno, proporcionando calidez sin sacrificar el estilo."),
    Producto(2, "https://i.pinimg.com/1200x/74/3d/f2/743df24b29adbae5a350d9b775b2cc59.jpg", "Pantalón Cargo Ajustado", 1999.00,descripcion = "Abrigo largo y elegante hecho de una suave mezcla de lana y cachemira. Ideal para los días fríos de invierno, proporcionando calidez sin sacrificar el estilo."),
    Producto(3, "https://i.pinimg.com/736x/ba/97/ba/ba97ba2a7b90299fef9e9d1f6ea66688.jpg", "Camiseta Oversize", 449.00,descripcion = "Abrigo largo y elegante hecho de una suave mezcla de lana y cachemira. Ideal para los días fríos de invierno, proporcionando calidez sin sacrificar el estilo."),
    Producto(4, "https://i.pinimg.com/736x/24/3e/55/243e55856df243674ead78bbb3b13572.jpg", "Chaqueta Piel Sintética", 800.00,descripcion = "Abrigo largo y elegante hecho de una suave mezcla de lana y cachemira. Ideal para los días fríos de invierno, proporcionando calidez sin sacrificar el estilo."),
    Producto(5, "https://ss840.suburbia.com.mx/xl/5012409496.jpg", "Jersey de Lana Gruesa", 349.00, descripcion = "Abrigo largo y elegante hecho de una suave mezcla de lana y cachemira. Ideal para los días fríos de invierno, proporcionando calidez sin sacrificar el estilo."),
    Producto(6, "https://i.pinimg.com/736x/f8/ec/3d/f8ec3d24e49f4af672dc64c49858cf8d.jpg", "Falda Plisada Midi", 400.00, descripcion = "Abrigo largo y elegante hecho de una suave mezcla de lana y cachemira. Ideal para los días fríos de invierno, proporcionando calidez sin sacrificar el estilo."),
    Producto(7, "https://i.pinimg.com/736x/1d/8b/d7/1d8bd75fac3784591e74202ace0da662.jpg", "Zapatillas Urbanas", 1699.00, descripcion = "Abrigo largo y elegante hecho de una suave mezcla de lana y cachemira. Ideal para los días fríos de invierno, proporcionando calidez sin sacrificar el estilo."),
    Producto(8, "https://i.pinimg.com/1200x/86/28/95/862895b757c67b9dc628a06688dedeb1.jpg", "Bolso de Hombro", 699.00, descripcion = "Abrigo largo y elegante hecho de una suave mezcla de lana y cachemira. Ideal para los días fríos de invierno, proporcionando calidez sin sacrificar el estilo."),
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleProducto(
    navController: NavController,
    productoId: Int,
    viewModel: CarritoViewModel
    ) {
    val producto = productosDeEjemplo.find { it.id == productoId }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(producto?.nombre ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (producto != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {

                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Nombre y precio
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = producto.precio.toCurrencyString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))


                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(32.dp))


                Button(
                    onClick =
                        { if (producto != null) {
                            viewModel.agregarProductoAlCarrito(producto)
                        navController.navigate("pantalla_carrito")
                    }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Añadir al Carrito")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: Producto no encontrado.")
            }
        }
    }
}
@Composable
fun ItemRopa(producto: Producto, onClick: (Producto) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(producto) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            AsyncImage(
                model = producto.imagenUrl,
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = producto.precio.toCurrencyString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
fun Double.toCurrencyString(): String {
    return "$${String.format("%.2f", this)}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMiRopa(navController:NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    icon = {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Inicio"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("pantalla_principal")
                    }
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text("Mi cuenta") },
                    icon = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Cuenta"
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch { scope.launch { drawerState.close() }
                            navController.navigate("pantalla_cuenta") }
                    }
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text("Ropa") },
                    icon = {
                        Icon(
                            Icons.Filled.Checkroom,
                            contentDescription = "Ropa"
                        )
                    },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }}
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text("Carrito de Compras") },
                    icon = {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Carrito"
                        )
                    },
                    selected = false,
                    onClick = {scope.launch { drawerState.close() }
                    navController.navigate("pantalla_carrito")}
                )
                Divider()
            }
        },
        content = {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                "VESTIGE",
                                fontSize = 50.sp,
                                color = Color.Black,
                                fontFamily = rsmFamily,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Abrir menú lateral",
                                    modifier = Modifier.size(100.dp)
                                )
                            }
                        },
                    )
                },

                ) { paddingValues ->
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productosDeEjemplo) { producto ->
                        ItemRopa(producto = producto) { clickedProduct ->
                            navController.navigate("pantalla_detalle/${clickedProduct.id}")
                        }
                    }
                }
            }
        }
    )
}

