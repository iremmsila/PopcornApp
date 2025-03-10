package com.example.popcorn.realm

import com.example.popcorn.api.response.Genre
import com.example.popcorn.api.response.Movie
import com.example.popcorn.realm.entity.GenreEntity
import com.example.popcorn.realm.entity.MovieEntity
import io.realm.Realm
import io.realm.kotlin.where

fun fetchMoviesFromRealm(): List<MovieEntity> {
    val realm = Realm.getDefaultInstance()
    return try {
        val movies = realm.where(MovieEntity::class.java).findAll()
        realm.copyFromRealm(movies)
    } finally {
        realm.close()
    }
}

fun fetchGenresFromRealm(): List<GenreEntity> {
    val realm = Realm.getDefaultInstance()
    return try {
        val genres = realm.where(GenreEntity::class.java).findAll()
        realm.copyFromRealm(genres)
    } finally {
        realm.close()
    }
}

fun saveGenresToRealm(genres: List<Genre>, moviesByGenre: Map<Int, List<Movie>>) {
    val realm = Realm.getDefaultInstance()
    realm.executeTransaction { transactionRealm ->
        genres.forEach { genre ->
            val movieEntities = moviesByGenre[genre.id]?.map { movie ->
                MovieEntity().apply {
                    movieId = movie.id
                    title = movie.title
                    poster_path = movie.poster_path
                    vote_average = movie.vote_average
                    adult = movie.adult
                    backdrop_path = movie.backdrop_path
                    original_language = movie.original_language
                    original_title = movie.original_title
                    overview = movie.overview
                    popularity = movie.popularity
                    release_date = movie.release_date
                    video = movie.video
                    vote_count = movie.vote_count
                }
            } ?: emptyList()

            val genreEntity = GenreEntity().apply {
                genreId = genre.id
                name = genre.name
                movieList?.addAll(movieEntities)
            }

            transactionRealm.copyToRealmOrUpdate(genreEntity)
        }
    }
    realm.close()
}
