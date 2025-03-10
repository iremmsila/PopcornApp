package com.example.popcorn.pages.homePage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popcorn.api.response.Genre
import com.example.popcorn.api.response.Movie
import com.example.popcorn.api.retrofit.RetrofitInstance
import com.example.popcorn.realm.entity.FavoriteListEntity
import com.example.popcorn.realm.entity.GenreEntity
import com.example.popcorn.realm.entity.MovieEntity
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomePageViewModel : ViewModel() {

    private val _movieList = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movieList: StateFlow<List<MovieEntity>> = _movieList

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> get() = _genres

    private val _localGenres = MutableStateFlow<List<GenreEntity>>(emptyList())
    val localGenres: StateFlow<List<GenreEntity>> get() = _localGenres

    private val apiKey = "your-api-key"

    var isOfflineMode: Boolean = false

    init {
        fetchGenres()
    }

    fun fetchMoviesFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val realm = RealmManager.getRealmInstance()
            try {
                val movies = realm.where(MovieEntity::class.java).findAll()
                val movieListCopy = realm.copyFromRealm(movies)
                withContext(Dispatchers.Main) {
                    _movieList.value = movieListCopy
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                RealmManager.closeRealm()
            }
        }
    }



    fun fetchGenres() {
        viewModelScope.launch {
            try {
                if (!isOfflineMode) {
                    val genresResponse = RetrofitInstance.api.getGenres(apiKey)
                    if (genresResponse.isSuccessful) {
                        genresResponse.body()?.let {
                            _genres.value = it.genres
                            fetchMoviesByGenre()
                        }
                    }
                }
                getGenresFromRealm()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchMoviesByGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            val genreList = _genres.value
            val realm = RealmManager.getRealmInstance()

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
                            // API'den gelen verileri Realm formatına dönüştür
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


                            realm.executeTransaction { transactionRealm ->

                                val genreEntity = transactionRealm.where(GenreEntity::class.java)
                                    .equalTo("genreId", genre.id)
                                    .findFirst() ?: GenreEntity().apply {
                                    genreId = genre.id
                                    name = genre.name
                                }


                                genreEntity.movieList?.clear()
                                genreEntity.movieList?.addAll(movieEntities)

                                transactionRealm.copyToRealmOrUpdate(genreEntity)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                RealmManager.closeRealm()
            }
        }
    }

    fun getGenresFromRealm() {
        viewModelScope.launch(Dispatchers.IO) {
            val realm = RealmManager.getRealmInstance()
            try {
                val genreList = realm.where(GenreEntity::class.java).findAll()
                val genreListCopy = realm.copyFromRealm(genreList)
                withContext(Dispatchers.Main) {
                    _localGenres.value = genreListCopy
                }
            } finally {
                RealmManager.closeRealm()
            }
        }
    }


    fun getGenreById(genreId: Int): GenreEntity? {
        return localGenres.value.find { it.genreId == genreId }
    }

}