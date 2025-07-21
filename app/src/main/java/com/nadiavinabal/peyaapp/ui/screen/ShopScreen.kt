package com.nadiavinabal.peyaapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.nadiavinabal.peyaapp.data.Product
import com.nadiavinabal.peyaapp.viewmodel.ShopViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.nadiavinabal.peyaapp.ui.component.MyNavigationBar
import com.nadiavinabal.peyaapp.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    viewModel: ShopViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navigationBack: () -> Unit,
    navigationToShoppingCart: () -> Unit,
    navController: NavController,
    currentDestination: NavBackStackEntry?) {

    val products by viewModel.products.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        bottomBar = {
            MyNavigationBar(navController = navController, currentDestination = currentDestination)
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFBF9F9))
                .padding(innerPadding)
        ) {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text("Shop", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                },
                navigationIcon = {
                    IconButton(onClick = {navigationBack()}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFCF8F8))
            )

            SearchBar(searchQuery = searchQuery,
                onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) })
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(products) { product ->
                    ShopItemCard(
                        onAddToCart = { cartViewModel.addToCart(product.id) },
                        item = product
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

        }
    }

}


@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF181010))
        Text("Shop", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF181010))
        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color(0xFF181010))
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        shape = RoundedCornerShape(35),
        onValueChange = onSearchQueryChanged,
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF8A5C5C))
        },
        placeholder = { Text("Search for products", color = Color(0xFF8A5C5C)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFF1EAEA), shape = RoundedCornerShape(12.dp)),
    )
}

@Composable
fun ShopItemCard(item: com.nadiavinabal.peyaapp.model.Product,
                 onAddToCart: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text("Popular", color = Color(0xFF8A5C5C), fontSize = 12.sp)
            Text(item.name, fontWeight = FontWeight.Bold, color = Color(0xFF181010))
            Text(item.description, color = Color(0xFF8A5C5C), fontSize = 12.sp)
            Button(
                onClick = { onAddToCart()},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1EAEA)),
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF181010))
                Text(item.price.toString(), color = Color(0xFF181010), fontSize = 12.sp)
            }
        }
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(16 / 9f)
                .weight(1f)
        )
    }
}

