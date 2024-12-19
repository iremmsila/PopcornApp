package com.example.popcorn.pages.splashPage.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.popcorn.R
import com.example.popcorn.pages.splashPage.viewModel.SplashViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SplashScreen(navController: NavController, splashViewModel: SplashViewModel = viewModel()) {
    val isDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current // Context erişimi


    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val iconRes = if (isDarkTheme) R.mipmap.ic_popcorn_white else R.mipmap.ic_popcorn_black
    val textColor = if (isDarkTheme) Color.White else Color.Black

    val navigateToMain by splashViewModel.navigateToMain.observeAsState(false)

    if (navigateToMain) {
        navController.navigate("login"/*context.getString(R.string.main_screen*/) {
            popUpTo(context.getString(R.string.splash_screen)) { inclusive = true }
        }
    }

    // Splash Screen Arayüzü
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
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
                    color = textColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                )
            )
        }
    }
}

