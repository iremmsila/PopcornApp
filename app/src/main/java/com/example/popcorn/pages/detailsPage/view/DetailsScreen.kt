package com.example.popcorn.pages.detailsPage.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.popcorn.api.TmdbApi
import com.example.popcorn.realm.entity.MovieEntity
import com.example.popcorn.util.RatingBar
import io.realm.Realm

@SuppressLint("DefaultLocale")
@Composable
fun DetailsScreen(
    movieId: Int?,
    navHostController: NavHostController
) {
    val realm = Realm.getDefaultInstance()
    val movie = remember { mutableStateOf<MovieEntity?>(null) }

    LaunchedEffect(movieId) {
        movie.value = realm.where(MovieEntity::class.java)
            .equalTo("movieId", movieId)
            .findFirst()
    }

    if (movie.value == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    } else {
        val movieDetails = movie.value!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Backdrop Image
                val backDropImageState = rememberAsyncImagePainter(
                    model = TmdbApi.IMAGE_BASE_URL + movieDetails.backdrop_path
                ).state

                if (backDropImageState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = "Backdrop Image Error"
                        )
                    }
                } else {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        painter = rememberAsyncImagePainter(TmdbApi.IMAGE_BASE_URL + movieDetails.backdrop_path),
                        contentDescription = "Backdrop Image",
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

// Poster and Movie Info
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                // Poster Image
                Image(
                    modifier = Modifier
                        .size(width = 120.dp, height = 180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    painter = rememberAsyncImagePainter(TmdbApi.IMAGE_BASE_URL + movieDetails.poster_path),
                    contentDescription = movieDetails.title,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Movie Info Column
                Column {
                    Text(
                        text = movieDetails.title ?: "No Title",
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Rating Bar
                        RatingBar(
                            starsModifier = Modifier.size(18.dp),
                            rating = (movieDetails.vote_average?.div(2) ?: 0.0).toDouble()
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Rating Text
                        Text(
                            text = String.format("%.1f", (movieDetails.vote_average?.div(2) ?: 0.0)),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Language: ${movieDetails.original_language}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Release Date: ${movieDetails.release_date}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Votes: ${movieDetails.vote_count}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }


            Spacer(modifier = Modifier.height(48.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Overview",
                fontSize = 19.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = movieDetails.overview ?: "No Overview",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
