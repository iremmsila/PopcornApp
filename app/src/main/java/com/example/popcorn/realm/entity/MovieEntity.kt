package com.example.popcorn.realm.entity

import com.example.popcorn.api.response.Movie
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MovieEntity : RealmObject(){
    @PrimaryKey
    var movieId: Int? = 0

    var title: String? = null
    var poster_path: String? = null
    var vote_average: Double? = null
    var adult: Boolean? = null
    var backdrop_path: String? = null
    var original_language: String? = null
    var original_title: String? = null
    var overview: String? = null
    var popularity: Double? = null
    var release_date: String? = null
    var video: Boolean? = null
    var vote_count: Int? = null
}

fun MovieEntity.toMovieModel(): Movie {
    return Movie(
        id = this.movieId ?: 0,
        title = this.title ?: "No Title",
        poster_path = this.poster_path ?: "",
        vote_average = this.vote_average ?: 0.0,
        adult = this.adult ?: false,
        backdrop_path = this.backdrop_path ?: "",
        original_language = this.original_language ?: "",
        original_title = this.original_title ?: "",
        overview = this.overview ?: "",
        popularity = this.popularity ?: 0.0,
        release_date = this.release_date ?: "",
        video = this.video ?: false,
        vote_count = this.vote_count ?: 0
    )
}
