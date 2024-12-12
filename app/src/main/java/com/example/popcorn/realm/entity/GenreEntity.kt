package com.example.popcorn.realm.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class GenreEntity : RealmObject() {

    @PrimaryKey
    var genreId: Int? = 0
    var name: String? = null


    var movie: MovieEntity? = null

    var movieList: RealmList<MovieEntity>? = RealmList()
}
