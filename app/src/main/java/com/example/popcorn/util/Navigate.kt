package com.example.popcorn.util

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.popcorn.pages.authentication.LoginPage
import com.example.popcorn.pages.authentication.SignupPage
import com.example.popcorn.pages.authentication.SplashScreen
import com.example.popcorn.pages.detailsPage.view.DetailsScreen
import com.example.popcorn.pages.homePage.view.MovieScreen
import com.example.popcorn.pages.moviesPage.GenreMoviesScreen
import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavHostController
import com.example.popcorn.pages.authentication.viewmodel.AuthViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    innerPadding: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable(
            "splash_screen",
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            "signup",
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            SignupPage(
                modifier = Modifier, // innerPadding olmadan test
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            "login",
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            LoginPage(
                modifier = Modifier, // innerPadding olmadan test
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            "home",
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            MovieScreen(navHostController = navController)
        }
        composable(
            "genre_movies/{genreId}",
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) { backStackEntry ->
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
            DetailsScreen(movieId = movieId, navHostController = navController)
        }
    }
}
