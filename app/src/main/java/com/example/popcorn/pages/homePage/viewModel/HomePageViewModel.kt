package com.example.popcorn.pages.homePage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popcorn.api.response.Genre
import com.example.popcorn.api.response.Movie
import com.example.popcorn.api.retrofit.RetrofitInstance
import com.example.popcorn.realm.entity.FavoriteListEntity
import com.example.popcorn.realm.entity.GenreEntity
import com.example.popcorn.realm.entity.MovieEntity
import com.example.popcorn.realm.entity.PersonDetailEntity
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import androidx.lifecycle.viewModelScope
import com.example.popcorn.api.TmdbApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageViewModel : ViewModel() {

    private val _movieList = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movieList: StateFlow<List<MovieEntity>> = _movieList

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> get() = _genres

    private val _moviesByGenre = MutableStateFlow<Map<Int, List<Movie>>>(emptyMap())
    val moviesByGenre: StateFlow<Map<Int, List<Movie>>> get() = _moviesByGenre

    private val _localGenres = MutableStateFlow<List<GenreEntity>>(emptyList())
    val localGenres: StateFlow<List<GenreEntity>> get() = _localGenres

    private val apiKey = "8c751fd9653cd578e62a1a4fabd9acb2"

    // Çevrimdışı kontrol bayrağı
    var isOfflineMode: Boolean = false

    init {
        fetchGenres()
    }

    fun fetchMoviesFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val realm = Realm.getDefaultInstance()
            try {
                val movies = realm.where(MovieEntity::class.java).findAll()
                val movieListCopy = realm.copyFromRealm(movies) // Copy Realm objects to avoid thread issues
                // Return to the main thread to update the UI
                withContext(Dispatchers.Main) {
                    _movieList.value = movieListCopy
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
               // realm.close() // Ensure Realm is closed on the correct thread
            }
        }
    }

    fun fetchGenres() {
        viewModelScope.launch {
            try {
                if (!isOfflineMode) { // Çevrimdışı modda API çağrılarını atla
                    val genresResponse = RetrofitInstance.api.getGenres(apiKey)
                    if (genresResponse.isSuccessful) {
                        genresResponse.body()?.let {
                            _genres.value = it.genres
                            fetchMoviesByGenre()
                        }
                    } else {
                        println("Failed to fetch genres: ${genresResponse.errorBody()?.string()}")
                    }
                }
                getGenresFromRealm() // Her durumda veritabanındaki türleri al
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error fetching genres: ${e.localizedMessage}")
            }
        }
    }

    fun fetchMoviesByGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            val genreList = _genres.value
            val realmDb = Realm.getDefaultInstance()

            try {
                genreList.forEach { genre ->
                    val moviesResponse = try {
                        RetrofitInstance.api.getMoviesByGenre(apiKey, genre.id)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }

                    if (moviesResponse?.isSuccessful == true) {
                        moviesResponse.body()?.let { movieResult ->
                            val movieEntities = movieResult.results.map { movie ->
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
                            }

                            realmDb.executeTransaction { transactionRealm ->
                                val genreEntity = GenreEntity().apply {
                                    genreId = genre.id
                                    name = genre.name
                                    movieList?.addAll(movieEntities)
                                }
                                transactionRealm.copyToRealmOrUpdate(genreEntity)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                //realmDb.close() // Always close Realm on the same thread
            }
        }
    }

    fun getGenresFromRealm() {
        viewModelScope.launch(Dispatchers.IO) {
            val realmDb = Realm.getDefaultInstance()
            try {
                val genreList = realmDb.where(GenreEntity::class.java).findAll()
                val genreListCopy = realmDb.copyFromRealm(genreList)
                withContext(Dispatchers.Main) {
                    _localGenres.value = genreListCopy // Emit to StateFlow
                }
            } finally {
                realmDb.close()
            }
        }
    }




//    fun fetchMoviesByGenre() {
//        viewModelScope.launch {
//            val genreList = _genres.value
//            val moviesMap = mutableMapOf<Int, List<Movie>>()
//
//            genreList.forEach { genre ->
//                try {
//                    val moviesResponse = RetrofitInstance.api.getMoviesByGenre(apiKey, genre.id)
//                    if (moviesResponse.isSuccessful) {
//                        moviesResponse.body()?.let {
//                            moviesMap[genre.id] = it.results
//                        }
//                    } else {
//                        println("Failed to fetch movies for genre ${genre.name}: ${moviesResponse.errorBody()?.string()}")
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    println("Error fetching movies for genre ${genre.name}: ${e.localizedMessage}")
//                }
//            }
//
//            val genresResponse = RetrofitInstance.api.getGenres(apiKey)
//            if (genresResponse.isSuccessful) {
//                genresResponse.body()?.let {
//                    _genres.value = it.genres
//                    fetchMoviesByGenre()
//                }
//            } else {
//                println("Failed to fetch genres: ${genresResponse.errorBody()?.string()}")
//            }
//
//            _moviesByGenre.value = moviesMap
//
//
//            CoroutineScope(Dispatchers.IO).launch {
//
//                val personalDetail = PersonDetailEntity().apply {
//                    _id = 1
//                    name = "t"
//                    surname = "t"
//                    mail = "t"
//                    password = "t"
//                    favorite = FavoriteListEntity().apply{
//                        movieId = 1
//                        title = "t"
//                        poster_path = "t"
//                        vote_average = 1.0
//                        adult = true
//                        backdrop_path = "t"
//                        original_language = "t"
//                        original_title = "t"
//                        overview = "t"
//                        popularity = 1.0
//                        release_date = "t"
//                        video = true
//                        vote_count = 1
//                    }
//                    favoriteList = RealmList()
//                }
//
//
//
//                val movieDetail = GenreEntity().apply {
//                    genreId =
//                        name = "ffff"
//                    movie = MovieEntity().apply{
//                        movieId = 1
//                        title = "f"
//                        poster_path = "f"
//                        vote_average = 1.0
//                        adult = true
//                        backdrop_path = "tf"
//                        original_language = "tf"
//                        original_title = "tf"
//                        overview = "tf"
//                        popularity = 1.0
//                        release_date = "tf"
//                        video = true
//                        vote_count = 1
//                    }
//                    movieList = RealmList()
//                }
//
//                var realmDb = Realm.getDefaultInstance()
//
//                realmDb.beginTransaction()
//
//                realmDb.copyToRealmOrUpdate(personalDetail)
//                realmDb.copyToRealmOrUpdate(movieDetail)
//
//                realmDb.commitTransaction()
//
//                realmDb.close()
//
//
//                //update
//                realmDb = Realm.getDefaultInstance()
//
//                realmDb.executeTransaction {realm ->
//                    var data = realm.where(PersonDetailEntity::class.java)
//                        .equalTo("_id", 1L)
//                        .findFirst()
//
//                    data?.name = "kest"
//                }
//                realmDb.close()
//
//            }
//        }
//    }
}
