package com.nadiavinabal.peyaapp.ui.screen

import android.graphics.ImageDecoder
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil3.Uri
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.toCoilUri
import com.nadiavinabal.peyaapp.viewmodel.ProfileViewModel
import com.nadiavinabal.peyaapp.model.Profile
import com.nadiavinabal.peyaapp.ui.component.MyNavigationBar

@Composable
fun ProfileScreen (
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
    currentDestination: NavBackStackEntry?
) {
    val profile by profileViewModel.profile.collectAsState()
    var name by remember(profile.firstName) { mutableStateOf(profile.firstName)}
    var lastName by remember(profile.lastName) { mutableStateOf(profile.lastName)}
    var email by remember(profile.email) { mutableStateOf(profile.email)}
    var password by remember(profile.password) { mutableStateOf(profile.password)}
    var nationality by remember(profile.nationality) { mutableStateOf(profile.nationality)}

    val registerStatus by profileViewModel.registerStatus.collectAsState()

    LaunchedEffect(registerStatus) {
        registerStatus?.let { result ->
            result.onSuccess {
                val context = null
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
            }.onFailure {
                val context = null
                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    val isImageUploading by profileViewModel.isImageUploading.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null)}
        val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            profileViewModel.setImageUri(it)
        }
    }

    LaunchedEffect(profile) {
        name = profile.firstName
        lastName = profile.lastName
        email = profile.email
        password = profile.password
        nationality = profile.nationality
    }
    Scaffold(
        bottomBar = {
            MyNavigationBar(navController = navController, currentDestination = currentDestination)
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //Alert Dialog por image
            if(isImageUploading) {
                AlertDialog(
                    onDismissRequest = {},
                    confirmButton = {},
                    title = { Text("Subiendo imagen")},
                    text = {
                        Column (
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            CircularProgressIndicator()
                        }
                    }
                )
            }
            val painter = when {
                imageUri != null -> rememberAsyncImagePainter(model = imageUri)
                profile.image.isNotEmpty() -> rememberAsyncImagePainter(model = profile.image)
                else -> null
            }

            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = "Imagen de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable { launcher.launch("image/*") }
                )
            }
            else {
                Box(modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { launcher.launch("image/*")},
                    contentAlignment = Alignment.Center){
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(64.dp))
                }
            }
            Spacer(Modifier.height(16.dp))
            Log.d("ProfileScreen", "URL de imagen: ${profile.image}")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it},
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it},
                label = { Text("Apellido") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it},
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it},
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            Button(
               /* onClick = {
                    val updatedProfile = Profile(
                        firstName = name,
                        lastName = lastName,
                        email = email,
                        password = password,
                        nationality = nationality,
                        image = profile.image
                    )
                    profileViewModel.updateProfile(updatedProfile, imageUri as android.net.Uri?)
                }, */
                onClick = {
                    profileViewModel.registerUser(
                        email = email,
                        fullName = "$name $lastName",
                        password = password
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar perfil")
            }
        }
    }

}



