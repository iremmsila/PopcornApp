package com.example.popcorn.realm

import com.example.popcorn.api.response.Movie
import com.example.popcorn.realm.entity.TrendingMovieEntity
import io.realm.Realm

fun saveTrendingMoviesToRealm(movies: List<Movie>) {
    val realm = Realm.getDefaultInstance()
    realm.executeTransaction { transactionRealm ->
        movies.forEach { movie ->
            val trendingMovieEntity = TrendingMovieEntity().apply {
                movieId = movie.id
                title = movie.title
                posterPath = movie.poster_path
                backdropPath = movie.backdrop_path
                voteAverage = movie.vote_average
            }
            transactionRealm.insertOrUpdate(trendingMovieEntity)
        }
    }
    realm.close()
}
