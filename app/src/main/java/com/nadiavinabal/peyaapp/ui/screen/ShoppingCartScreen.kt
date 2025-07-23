package com.nadiavinabal.peyaapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.nadiavinabal.peyaapp.viewmodel.CartViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nadiavinabal.peyaapp.database.entities.CartItemWithProductEntity
import com.nadiavinabal.peyaapp.ui.component.MyAppTopBar
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.nadiavinabal.peyaapp.ui.component.MyNavigationBar

@Composable
fun ShoppingCartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit,
    navController: NavController,
    currentDestination: NavBackStackEntry?,
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalItems by cartViewModel.totalItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()
    val isCheckoutDone by cartViewModel.isCheckoutDone.collectAsState()

    var showCheckoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyAppTopBar(
                title = "Shopping Cart",
                isDarkTheme = isDarkTheme,
                toggleTheme = toggleTheme
            )
        },
        bottomBar = {
            MyNavigationBar(
                navController = navController, currentDestination = currentDestination
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tu carrito está vacío",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartItems) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = item.product.imageUrl,
                                    contentDescription = item.product.productName,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.product.productName,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "$${item.product.price}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(onClick = {
                                            cartViewModel.decreaseQuantity(item.cartItemEntity)
                                        }) {
                                            Icon(Icons.Default.Remove, contentDescription = "Disminuir")
                                        }
                                        Text(item.cartItemEntity.quantity.toString())
                                        IconButton(onClick = {
                                            cartViewModel.increaseQuantity(item.cartItemEntity)
                                        }) {
                                            Icon(Icons.Default.Add, contentDescription = "Aumentar")
                                        }
                                    }
                                }

                                IconButton(onClick = {
                                    cartViewModel.removeItem(item.cartItemEntity)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar"
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color.Gray))
                Spacer(modifier = Modifier.height(16.dp))

                Text("Total de productos: $totalItems", style = MaterialTheme.typography.bodyLarge)
                Text("Total general: $${"%.2f".format(totalPrice)}", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        cartViewModel.checkout()
                        showCheckoutDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalizar compra")
                }
            }
        }

        // ✅ AlertDialog al finalizar la compra
        if (showCheckoutDialog && isCheckoutDone) {
            AlertDialog(
                onDismissRequest = { showCheckoutDialog = false },
                title = { Text("Compra realizada") },
                text = { Text("Tu pedido ha sido procesado exitosamente.") },
                confirmButton = {
                    TextButton(onClick = { showCheckoutDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}





