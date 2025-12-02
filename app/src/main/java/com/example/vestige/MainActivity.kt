package com.example.vestige

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vestige.ui.CarritoViewModel
import com.example.vestige.ui.CarritoViewModelProvider
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vestige.ui.theme.rsmFamily
import kotlinx.coroutines.launch
import androidx.navigation.NavType


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appContainer = (application as VestigeApplication).container
        val carritoFactory = CarritoViewModelProvider.Factory(appContainer.carritoRepository)
        setContent {
            MyAppRopa(viewModelFactory = carritoFactory)
        }
    }
}
@Composable
fun MyAppRopa(viewModelFactory: androidx.lifecycle.ViewModelProvider.Factory){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "pantalla_principal"

    ){
        composable("pantalla_principal"){PantallaPrincipal(navController = navController)}
        composable("pantalla_cuenta"){PantallaMiCuenta(navController)}
        composable("pantalla_Iniciarsesion"){PantallaIniciarSesion(navController)}
        composable("pantallla_miRopa"){PantallaMiRopa(navController)}
        composable(route = "pantalla_detalle/{productoId}", arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productoId")

            if (productId != null) {
                PantallaDetalleProducto(
                    navController = navController,
                    productoId = productId,
                    viewModel = viewModel(factory = viewModelFactory)
                )
            }
        }
        composable(route = "pantalla_carrito") { PantallaCarrito(
            navController = navController,
            viewModel = viewModel(factory = viewModelFactory)
        )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))

                NavigationDrawerItem(
                    label = {Text("Inicio")},
                    icon = { Icon (Icons.Filled.Home,
                        contentDescription = "Inicio" )},
                    selected = false,
                    onClick = {scope.launch { drawerState.close() }
                        navController.navigate("pantalla_principal")}
                )
                Divider()
                NavigationDrawerItem(
                    label = {Text("Mi cuenta")},
                    icon = { Icon (Icons.Filled.Person,
                        contentDescription = "Cuenta" )},
                    selected = false,
                    onClick = {scope.launch { drawerState.close() }
                        navController.navigate("pantalla_cuenta")}
                )
                Divider()
                NavigationDrawerItem(
                    label = {Text("Ropa")},
                    icon = { Icon (Icons.Filled.Checkroom,
                        contentDescription = "Ropa" )},
                    selected = false,
                    onClick = {scope.launch { drawerState.close() }
                    navController.navigate("pantallla_miRopa")}
                )
                Divider()
                NavigationDrawerItem(
                    label = {Text("Carrito de Compras")},
                    icon = { Icon (Icons.Filled.ShoppingCart,
                        contentDescription = "Carrito" )},
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
                            )},
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
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Spacer(Modifier.height(50.dp))
                    Text(
                        "NUEVOS PRODUCTOS",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    val carouselState = rememberCarouselState { 5 }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        HorizontalMultiBrowseCarousel(
                            state = carouselState,
                            preferredItemWidth = 600.dp,
                            itemSpacing = 10.dp,
                        ) { page ->
                            Box(modifier = Modifier.size(600.dp)){
                                Image(
                                    painter = painterResource(
                                        id = when (page) {
                                            0 -> R.drawable.chaquetas
                                            1 -> R.drawable.mujeres
                                            2 -> R.drawable.chaqueta
                                            3 -> R.drawable.mezclilla
                                            else -> R.drawable.mujeres2
                                        }
                                    ),
                                    contentDescription = "Imagen del carrusel $page",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                        }
                    }


                }
            }
        }
    )
}





