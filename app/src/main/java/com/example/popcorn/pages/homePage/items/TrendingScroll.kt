package com.example.popcorn.pages.homePage.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.popcorn.realm.entity.TrendingMovieEntity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.delay

@Composable
fun AutoScrollingTrendingMoviesPager() {
    val realm = Realm.getDefaultInstance()
    val movies = remember { mutableStateListOf<TrendingMovieEntity>() }

    // Realm'den filmleri çek
    LaunchedEffect(Unit) {
        val realmResults = realm.where<TrendingMovieEntity>().findAll()
        movies.addAll(realmResults)
    }

    val pagerState = rememberPagerState(pageCount = { movies.size })

    // Otomatik geçiş için
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // 3 saniyede bir geçiş
            val nextPage = (pagerState.currentPage + 1) % movies.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    // HorizontalPager ile gösterim
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(48.dp))
    ) { page ->
        val movie = movies[page]
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.backdropPath}"),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = movie.title,
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                color = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}
