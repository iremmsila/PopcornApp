package com.example.popcorn.realm.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FavoriteListEntity : RealmObject() {

    @PrimaryKey
    var _id: Long? = 0
    var movieId: Long? = 0

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