package com.example.popcorn.pages.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.popcorn.R
import com.example.popcorn.pages.authentication.viewmodel.AuthState
import com.example.popcorn.pages.authentication.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // Tema renklerini MaterialTheme'den al
    val colors = MaterialTheme.colorScheme
    val isDarkTheme = isSystemInDarkTheme()
    val iconRes = if (isDarkTheme) R.mipmap.ic_popcorn_white else R.mipmap.ic_popcorn_black


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp



    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    // Arayüz
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background) // Arka plan rengi
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo ve Başlık
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Popcorn Icon",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "\n\nPopcorn+",
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(screenHeight * 0.1f) // Ekranın %10'u kadar boşluk
            )


            // Log In Metni
            Text(
                text = "LOG IN",
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail", color = colors.onBackground) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email", tint = colors.primary) },
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.secondary,
                    cursorColor = colors.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = colors.onBackground) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password", tint = colors.primary) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.secondary,
                    cursorColor = colors.primary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                color = colors.onBackground.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )

            // Login Butonu
            Button(
                onClick = { authViewModel.login(email, password) },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary, // Buton arka plan rengi
                    contentColor = colors.onPrimary // Buton içindeki yazı rengi
                ),
                enabled = authState.value != AuthState.Loading
            ) {
                Text("Log In", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sign Up Linki
            Row(
                verticalAlignment = Alignment.CenterVertically, // İçerikleri dikey olarak hizala
            ) {
                Text(
                    "Don't have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp)) // Araya boşluk ekle
                TextButton(onClick = { navController.navigate("signup") }) {
                    Text("Sign Up", color = colors.secondary)
                }
            }

        }
    }
}
