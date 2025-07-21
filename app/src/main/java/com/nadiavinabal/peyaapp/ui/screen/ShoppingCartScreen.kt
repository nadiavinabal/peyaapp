package com.nadiavinabal.peyaapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(viewModel: CartViewModel = hiltViewModel()) {

    val cartItems by viewModel.cartItems.collectAsState()
    val totalItems by viewModel.totalItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()

    Scaffold(
        bottomBar = {
            Column {
                Button(
                    onClick = {viewModel.checkout()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE82630))
                ) {
                    Text("Pagar", color = Color(0xFFFCF8F8))
                }

                NavigationBar(containerColor = Color(0xFFFCF8F8)) {
                    NavigationBarItem(selected = false, onClick = {}, icon = {
                        Icon(Icons.Default.Home, contentDescription = "Inicio", tint = Color(0xFF974E52))
                    })
                    NavigationBarItem(selected = false, onClick = {}, icon = {
                        Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color(0xFF974E52))
                    })
                    NavigationBarItem(selected = true, onClick = {}, icon = {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color(0xFF1B0E0F))
                    })
                    NavigationBarItem(selected = false, onClick = {}, icon = {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color(0xFF974E52))
                    })
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFFCF8F8))
        ) {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text("Carrito", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFCF8F8))
            )
            if (cartItems.isEmpty()) {
                Text(
                    "Tu carrito está vacío",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                cartItems.forEach { itemWithProduct ->
                    val cartItem = itemWithProduct.cartItemEntity
                    val product = itemWithProduct.product

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = product.productName,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(product.productName, fontWeight = FontWeight.Medium, color = Color(0xFF1B0E0F))
                                Text("${product.price} c/u", color = Color(0xFF974E52))
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.decreaseQuantity(cartItem) }) {
                                Text("-", color = Color(0xFF1B0E0F))
                            }
                            Text("${cartItem.quantity}", modifier = Modifier.width(24.dp), textAlign = TextAlign.Center)
                            IconButton(onClick = { viewModel.increaseQuantity(cartItem) }) {
                                Text("+", color = Color(0xFF1B0E0F))
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.removeItem(cartItem) },
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFF3E7E8))
                        ) {
                            Text("Eliminar", color = Color(0xFF1B0E0F))
                        }
                    }
                }

                Text("Resumen del pedido", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Cantidad de productos", color = Color(0xFF974E52))
                        Text("$totalItems", color = Color(0xFF1B0E0F))
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", color = Color(0xFF974E52))
                        Text("$${"%.2f".format(totalPrice)}", color = Color(0xFF1B0E0F))
                    }
                }
            }

        }
    }
}


