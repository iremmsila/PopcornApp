package com.example.popcorn.pages.homePage.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.popcorn.api.trendingApi
import com.example.popcorn.pages.homePage.items.AutoScrollingTrendingMoviesPager
import com.example.popcorn.pages.homePage.items.MovieItem
import com.example.popcorn.pages.homePage.viewModel.HomePageViewModel
import com.example.popcorn.pages.homePage.viewModel.fetchAndSaveTrendingMovies


@Composable
fun MovieScreen(viewModel: HomePageViewModel = viewModel(), navHostController: NavHostController) {

    LaunchedEffect(Unit) {
        viewModel.getGenresFromRealm()
        viewModel.fetchMoviesFromDatabase()
    }

    fetchAndSaveTrendingMovies(apiService = trendingApi, apiKey = "your-api-key")


    val genres = viewModel.localGenres.collectAsState().value
    var isExpanded by remember { mutableStateOf(true) }


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                AutoScrollingTrendingMoviesPager()
            }

            items(genres) { genre ->
                Spacer(modifier = Modifier.height(16.dp))

                genre.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, bottom = 8.dp)
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    // Show first 10 movies
                    items(genre.movieList.orEmpty().take(10)) { movie ->
                        MovieItem(movie = movie, navHostController = navHostController)
                    }


                    item {
                        Row(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    navHostController.navigate("genre_movies/${genre.genreId}")
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    isExpanded = !isExpanded
                                    navHostController.navigate("genre_movies/${genre.genreId}")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Daha Fazla",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }


  //  }
}


