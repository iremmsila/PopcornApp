package com.example.popcorn.realm.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PersonDetailEntity : RealmObject() {

    @PrimaryKey
    var _id: Long? = null


    var name: String?= null
    var surname: String?= null
    var mail: String?= null
    var password: String?= null
    var favorite: FavoriteListEntity? = null

    var favoriteList: RealmList<FavoriteListEntity>? = RealmList()
}