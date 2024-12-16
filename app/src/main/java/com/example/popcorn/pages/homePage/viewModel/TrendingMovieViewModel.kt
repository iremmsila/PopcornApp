package com.example.popcorn.pages.homePage.viewModel

import com.example.popcorn.api.TrendingMovieApi
import com.example.popcorn.realm.saveTrendingMoviesToRealm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun fetchAndSaveTrendingMovies(apiService: TrendingMovieApi, apiKey: String) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = apiService.getTrendingMovies(apiKey)
            saveTrendingMoviesToRealm(response.results)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
