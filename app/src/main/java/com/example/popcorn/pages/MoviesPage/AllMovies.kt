package com.example.popcorn.pages.moviesPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.popcorn.pages.homePage.viewModel.HomePageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.popcorn.R

@Composable
fun GenreMoviesScreen(
    genreId: Int,
    viewModel: HomePageViewModel = viewModel(),
    navHostController: NavHostController
) {
    val genre = viewModel.getGenreById(genreId)
    val movies = genre?.movieList.orEmpty()
    val isDarkTheme = isSystemInDarkTheme()
    val iconRes = if (isDarkTheme) R.mipmap.ic_popcorn_white else R.mipmap.ic_popcorn_black

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Popcorn Icon",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = ("\n" + genre?.name) ?: "",
                style = TextStyle(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                )
            )
        }


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
