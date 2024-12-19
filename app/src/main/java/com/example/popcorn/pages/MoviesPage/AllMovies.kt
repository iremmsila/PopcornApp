package com.example.popcorn.pages.moviesPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.popcorn.pages.homePage.viewModel.HomePageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun GenreMoviesScreen(
    genreId: Int,
    viewModel: HomePageViewModel = viewModel(),
    navHostController: NavHostController
) {
    val genre = viewModel.getGenreById(genreId)
    val movies = genre?.movieList.orEmpty()

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Title at the top of the screen
        Text(
            text = genre?.name ?: "Movies",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                MovieItemsMore(movie = movie, navHostController = navHostController)
            }
        }
    }
}
