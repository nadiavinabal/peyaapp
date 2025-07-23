package com.nadiavinabal.peyaapp.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.nadiavinabal.peyaapp.viewmodel.ShopViewModel



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.nadiavinabal.peyaapp.ui.component.MyAppTopBar
import com.nadiavinabal.peyaapp.ui.component.MyNavigationBar
import com.nadiavinabal.peyaapp.viewmodel.CartViewModel

@Composable
fun ShopScreen(
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit,
    navController: NavController,
    currentDestination: NavBackStackEntry?,
    viewModel: ShopViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var sortAscending by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("shop") }

    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    val filteredProducts = products
        .filter { it.name.contains(searchQuery.text, ignoreCase = true) }
        .sortedBy { if (sortAscending) it.price else -it.price }

    Scaffold(
        topBar = {
            MyAppTopBar(
                title = "Productos",
                isDarkTheme = isDarkTheme,
                toggleTheme = toggleTheme
            )
        },
        bottomBar = {
            MyNavigationBar(
                navController = navController, currentDestination = currentDestination
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar producto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box {
                Button(onClick = { expanded = true }) {
                    Text(if (sortAscending) "Precio: Ascendente" else "Precio: Descendente")
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(text = { Text("Ascendente") }, onClick = {
                        sortAscending = true
                        expanded = false
                    })
                    DropdownMenuItem(text = { Text("Descendente") }, onClick = {
                        sortAscending = false
                        expanded = false
                    })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredProducts) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(Modifier.fillMaxSize()) {
                                AsyncImage(
                                    model = product.imageUrl,
                                    contentDescription = product.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp)
                                        .background(Color.Black.copy(alpha = 0.4f)),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            product.name,
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                        Text(
                                            product.description,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                        Text(
                                            "$${product.price}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                        Text(
                                            product.category ?: "General",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        IconButton(onClick = {
                                            cartViewModel.addToCart(product._id) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Agregar al carrito",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

