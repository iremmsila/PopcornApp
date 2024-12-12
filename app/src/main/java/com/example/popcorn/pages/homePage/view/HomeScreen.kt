package com.example.popcorn.pages.homePage.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.popcorn.pages.homePage.items.MovieItem
import com.example.popcorn.pages.homePage.viewModel.HomePageViewModel
import com.example.popcorn.realm.entity.toMovieModel

@Composable
fun MovieScreen(viewModel: HomePageViewModel = viewModel(), navHostController: NavHostController) {
    // Verileri yükleme
    LaunchedEffect(Unit) {
        viewModel.getGenresFromRealm() // Veritabanından türleri al
        viewModel.fetchMoviesFromDatabase() // Veritabanından filmleri al
    }

    // Veritabanından gelen türleri dinle
    val genres = viewModel.localGenres.collectAsState().value

    if (genres.isEmpty()) {
        // Eğer türler boşsa yükleniyor mesajı göster
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Loading genres...",
                style = MaterialTheme.typography.titleLarge
            )
        }
    } else {
        // Türleri ve filmleri listele
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            genres.forEach { genre ->
                item {
                    genre.name?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(genre.movieList.orEmpty()) { movie ->
                            MovieItem(movie = movie, navHostController = navHostController)
                        }
                    }
                }
            }
        }
    }
}



//@Composable
//fun MovieScreen(viewModel: HomePageViewModel = viewModel(), navHostController: NavHostController) {
//    // Load genres and movies from Realm
//    LaunchedEffect(Unit) {
//        viewModel.getGenresFromRealm()
//        viewModel.fetchMoviesByGenre()
//        viewModel.fetchMoviesFromDatabase()
//    }
//
//    val genres = viewModel.localGenres.collectAsState().value
//    val movies by viewModel.movieList.collectAsState()
//
//    if (genres.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Loading genres...",
//                style = MaterialTheme.typography.titleLarge
//            )
//        }
//    } else {
//        LazyColumn(
//            modifier = Modifier.padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            items(genres) { genre ->
//                genre.name?.let {
//                    Text(
//                        text = it,
//                        style = MaterialTheme.typography.headlineMedium,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                }
//
//                LazyRow(
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(genre.movieList.orEmpty()) { movie ->
//                        MovieItem(movie = movie, navHostController = navHostController)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun MovieScreen(viewModel: HomePageViewModel = viewModel(), navHostController: NavHostController) {
//    // Filmleri yüklemek için LaunchedEffect kullanıyoruz
//    LaunchedEffect(Unit) {
//        viewModel.fetchMoviesFromDatabase()
//    }
//
//    val movieList by viewModel.movieList.collectAsState() // StateFlow'u dinliyoruz
//
//    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        modifier = Modifier.padding(16.dp)
//    ) {
//        items(movieList) { movieEntity ->
//            MovieItem(
//                movie = movieEntity,
//                navHostController = navHostController
//            )
//        }
//    }
//}
