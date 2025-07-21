package com.nadiavinabal.peyaapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nadiavinabal.peyaapp.database.entities.OrderEntity
import com.nadiavinabal.peyaapp.viewmodel.OrderHistoryViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.lazy.items
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel = hiltViewModel(),
    onBackToProfile: () -> Unit,
    navigationBack: () -> Unit,
) {
    val orders by viewModel.orders.collectAsState()

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text("Historial de pedidos", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                },
                navigationIcon = {
                    IconButton(onClick = {navigationBack()}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFCF8F8))
            )
        },
        bottomBar = {
            Button(
                onClick = onBackToProfile,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE82630))
            ) {
                Text("Volver al perfil", color = Color.White)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFFFCF8F8))
                .fillMaxSize()
        ) {
            items(orders) { order ->
               OrderItemCard(order)
           }
        }
    }
}

@Composable
fun OrderItemCard(order: OrderEntity) {
    val formattedDate = remember(order.orderData) {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        sdf.format(Date(order.orderData))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Fecha: $formattedDate", fontWeight = FontWeight.Bold, color = Color(0xFF1B0E0F))
            Spacer(modifier = Modifier.height(4.dp))
            Text("Total: $${"%.2f".format(order.totalAmount)}", color = Color(0xFF974E52))
        }
    }
}