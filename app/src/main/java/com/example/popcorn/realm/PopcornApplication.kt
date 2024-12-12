package com.example.popcorn.realm

import android.app.Application
import com.example.popcorn.pages.homePage.viewModel.HomePageViewModel

import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration


@HiltAndroidApp
class PopcornApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initiateRealm()
    }

    fun initiateRealm(){
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("Apppopcorn-db.realm")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1) // Sürümü artırdım
            .build()

        Realm.setDefaultConfiguration(config)
    }
}
