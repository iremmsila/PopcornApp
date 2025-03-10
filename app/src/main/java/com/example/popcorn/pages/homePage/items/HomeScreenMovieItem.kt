package com.example.popcorn.pages.homePage.items


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.popcorn.api.TmdbApi
import com.example.popcorn.api.response.Movie
import com.example.popcorn.realm.entity.MovieEntity


@Composable
fun MovieItem(
    movie: MovieEntity,
    navHostController: NavHostController
) {
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(TmdbApi.IMAGE_BASE_URL + movie.backdrop_path)
            .build()
    )

    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .clickable {
                navHostController.navigate("movie_detail/${movie.movieId}")
            },
    ) {
        if (imagePainter.state is AsyncImagePainter.State.Error) {
            Text(
                text = "Image not available",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        } else {
            Image(
                painter = imagePainter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(22.dp))
            )
        }

//        // Filmin başlığı ve diğer bilgilerini göstermek için
//        Text(
//            text = movie.title ?: "Unknown",
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(top = 8.dp)
//        )
    }
}
