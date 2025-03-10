package com.example.popcorn

import com.example.popcorn.api.response.Genre
import com.example.popcorn.api.TmdbApi
import com.example.popcorn.api.response.GenresResponse
import com.example.popcorn.api.response.MovieResponse
import com.example.popcorn.api.response.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class TmdbApiTest {

    @Mock
    private lateinit var tmdbApi: TmdbApi

    private val apiKey = "test_api_key"
    private val genreId = 23
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetGenres() = runBlocking {
        val mockGenresResponse = GenresResponse(
            genres = listOf(
                Genre(id = 4, name = "Action"),
                Genre(id = 5, name = "Comedy")
            )
        )

        Mockito.`when`(tmdbApi.getGenres(apiKey)).thenReturn(Response.success(mockGenresResponse))

        val response = tmdbApi.getGenres(apiKey)


        assert(response.isSuccessful)
        assert(response.body() != null)
        assert(response.body()?.genres?.isNotEmpty() == true)
        assert(response.body()?.genres?.get(0)?.name == "Action")
    }

    @Test
    fun testGetMoviesByGenre() = runBlocking {
        val mockMoviesResponse = MovieResponse(
            results = listOf(
                Movie(
                    id = 7,
                    title = "Action Movie 1",
                    poster_path = "/path1.jpg",
                    vote_average = 9.5,
                    adult = false,
                    backdrop_path = "/backdrop1.jpg",
                    original_language = "en",
                    original_title = "Original Action Movie 1",
                    overview = "This is an overview of Action Movie 1.",
                    popularity = 1500.0,
                    release_date = "2025-01-01",
                    video = false,
                    vote_count = 200,
                    updateTimestamp = System.currentTimeMillis()
                ),
                Movie(
                    id = 8,
                    title = "Action Movie 2",
                    poster_path = "/path2.jpg",
                    vote_average = 7.8,
                    adult = false,
                    backdrop_path = "/backdrop2.jpg",
                    original_language = "en",
                    original_title = "Original Action Movie 2",
                    overview = "This is an overview of Action Movie 2.",
                    popularity = 1200.0,
                    release_date = "2025-02-01",
                    video = false,
                    vote_count = 150,
                    updateTimestamp = System.currentTimeMillis()
                )
            )
        )

        Mockito.`when`(tmdbApi.getMoviesByGenre(apiKey, genreId)).thenReturn(Response.success(mockMoviesResponse))

        val response = tmdbApi.getMoviesByGenre(apiKey, genreId)


        assert(response.isSuccessful)
        assert(response.body() != null)
        assert(response.body()?.results?.isNotEmpty() == true)
        assert(response.body()?.results?.get(0)?.title == "Action Movie 1")
        assert(response.body()?.results?.get(0)?.poster_path == "/path1.jpg")
        assert(response.body()?.results?.get(0)?.vote_average == 9.5)
    }
}
