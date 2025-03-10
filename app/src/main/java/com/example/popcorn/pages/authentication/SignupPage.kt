package com.example.popcorn.pages.authentication

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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
fun SignupPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val colors = MaterialTheme.colorScheme
    val isDarkTheme = isSystemInDarkTheme()
    val iconRes = if (isDarkTheme) R.mipmap.ic_popcorn_white else R.mipmap.ic_popcorn_black

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.observeAsState(AuthState.Idle)
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp


    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate("home") {
                    popUpTo("signup") { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Başlık
            Text(
                text = "SIGN UP",
                fontSize = 45.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(32.dp))


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail", color = colors.onBackground) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email", tint = colors.primary) },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.secondary,
                    cursorColor = colors.primary
                )
            )

            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = colors.onBackground) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password", tint = colors.primary) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.secondary,
                    cursorColor = colors.primary

                )
            )

            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = { authViewModel.signup(email, password) },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                ),
                enabled = authState != AuthState.Loading
            ) {
                Text("Sign Up",
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                color = colors.onBackground.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onBackground
                )
                TextButton(onClick = { navController.navigate("login") }) {
                    Text("Login", color = colors.secondary)
                }
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.1f))


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
                        fontFamily = FontFamily.Cursive
                    )
                )
            }
        }
    }

}