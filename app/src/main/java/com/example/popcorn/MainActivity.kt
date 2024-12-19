package com.example.popcorn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.popcorn.pages.authentication.LoginPage
import com.example.popcorn.pages.authentication.SignupPage
import com.example.popcorn.pages.authentication.viewmodel.AuthViewModel
import com.example.popcorn.pages.detailsPage.view.DetailsScreen
import com.example.popcorn.pages.homePage.view.MovieScreen
import com.example.popcorn.pages.moviesPage.GenreMoviesScreen

import com.example.popcorn.realm.entity.MovieEntity


import com.example.popcorn.ui.theme.PopcornTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PopcornTheme {
                val navController = rememberNavController()
                val movie = MovieEntity()
                val authViewModel : AuthViewModel by viewModels()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    //backgroundColor = MaterialTheme.colorScheme.background, // veya tercihinize göre başka bir renk
                    content = { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavHost(
                                navController = navController,
                                startDestination = "splash_screen"
                            ) {
                                composable("splash_screen") {
                                    LoginPage(modifier =  Modifier.padding(innerPadding),navController,authViewModel)
                                   // SplashScreen(navController = navController)
                                }
                                composable("signup"){
                                    SignupPage(modifier =  Modifier.padding(innerPadding),navController,authViewModel)
                                }
                                composable("login"){
                                    LoginPage(Modifier.padding(innerPadding),navController,authViewModel)
                                }
                                composable("main_screen") {
                                    MovieScreen(navHostController = navController)
                                }
                                composable("home") {
                                    MovieScreen(navHostController = navController)
                                }
                                composable("genre_movies/{genreId}") { backStackEntry ->
                                    val genreId = backStackEntry.arguments?.getString("genreId")?.toIntOrNull() ?: 0
                                    GenreMoviesScreen(genreId = genreId, navHostController = navController)
                                }
                                composable(
                                    route = "movie_detail/{movieId}",
                                    enterTransition = { slideInHorizontally() + fadeIn() },
                                    exitTransition = { slideOutHorizontally() + fadeOut() },
                                    arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                                ) { backStackEntry ->
                                    val movieId = backStackEntry.arguments?.getInt("movieId")
                                    DetailsScreen( movieId = movieId, navHostController = navController)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PopcornTheme {
        Greeting("Android")
    }
}