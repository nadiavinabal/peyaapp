package com.nadiavinabal.peyaapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadiavinabal.peyaapp.R
import com.nadiavinabal.peyaapp.viewmodel.LoginViewModel

@Composable
fun LoginScreen( loginViewModel: LoginViewModel = hiltViewModel(), navigationToShop: () -> Unit,
                 navigationToRegister: () -> Unit) {

    val uiState = loginViewModel.uiState
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Sign in",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 24.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF181111)
                )
            }

            OutlinedTextField(
                value = uiState.email,
                shape = RoundedCornerShape(30),
                onValueChange = { loginViewModel.onEmailChange(it) },
                label = { Text("Email") },
                isError = uiState.emailError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFF4F0F0), shape = RoundedCornerShape(16.dp)),
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF181111))
            )
            Text(
                text = "Please enter a valid email address.",
                color = Color(0xFF886364),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

            OutlinedTextField(
                value = uiState.password,
                shape = RoundedCornerShape(35),
                onValueChange = { loginViewModel.onPasswordChange(it) },
                placeholder = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFF4F0F0), shape = RoundedCornerShape(16.dp)),
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF181111)),
                visualTransformation = PasswordVisualTransformation()
            )
            Text(
                text = "Please enter a valid password.",
                color = Color(0xFF886364),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

            Button(
                onClick = { loginViewModel.login(
                    onSuccess = {
                        Toast.makeText(context, "Login exitoso", Toast.LENGTH_SHORT).show()
                        navigationToShop()
                    },
                    onError = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 16.dp),
                shape = CircleShape,
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE9242A))
            ) {
                Text(
                    text = "Sign in",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
            TextButton(
                onClick = { navigationToRegister() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "¿No tenés cuenta? Registrarme",
                    color = Color(0xFFE9242A),
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }
}

