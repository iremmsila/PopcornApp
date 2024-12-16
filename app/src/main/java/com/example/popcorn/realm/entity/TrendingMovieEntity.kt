package com.example.popcorn.realm.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TrendingMovieEntity : RealmObject() {
    @PrimaryKey
    var movieId: Int = 0
    var title: String = ""
    var posterPath: String? = null
    var backdropPath: String? = null
    var voteAverage: Double = 0.0
}
