
package com.example.popcorn.api.controls


import com.example.popcorn.api.TmdbApi
import com.example.popcorn.util.Resource
import com.example.popcorn.api.response.Movie
import io.realm.CollectionUtils.copyToRealm
import io.realm.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import io.realm.kotlin.ext.query



class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: TmdbApi,
    private val realm: Realm
) : MovieListRepository {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            // Yerel veritabanındaki filmleri getir
            val localMovies: List<Movie> = realm.query<Movie>("category == $0", category).find()

            // Eğer yerel veri varsa ve remote'dan zorla çekme yapılmıyorsa
            val shouldLoadLocalMovie = localMovies.isNotEmpty() && !forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(Resource.Success(data = localMovies))
                emit(Resource.Loading(false))
                return@flow
            }

            // API'den veriyi çek ve hata durumlarını ele al
            val movieListFromApi = try {
                movieApi.getMoviesByGenre(category, page)
            } catch (e: IOException) {
                emit(Resource.Error(message = "Error loading movies: Network issue"))
                return@flow
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Error loading movies: Server issue"))
                return@flow
            } catch (e: Exception) {
                emit(Resource.Error(message = "Error loading movies: Unknown issue"))
                return@flow
            }

            // API'den gelen veriyi Realm veritabanına kaydet
            realm.writeBlocking {
                movieListFromApi.results.forEach { movieDto ->
                    copyToRealm(movieDto.toMovieEntity(category))
                }
            }

            // UI'ya veriyi gönder
            val updatedMovies: List<Movie> = realm.query<Movie>("category == $0", category).find()
            emit(Resource.Success(data = updatedMovies))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            // Yerel veritabanından ID'ye göre filmi getir
            val movieEntity: Movie? = realm.query<Movie>("id == $0", id).first().find()

            if (movieEntity != null) {
                emit(Resource.Success(movieEntity))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error: No such movie found"))
            emit(Resource.Loading(false))
        }
    }

    suspend fun checkAndUpdateMovies(category: String, page: Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            // Veritabanında güncel veri var mı kontrol et
            val localMovies: List<Movie> = realm.query<Movie>("category == $0", category).find()
            if (localMovies.isNotEmpty()) {
                emit(Resource.Success(data = localMovies))
            }

            // API'den yeni veri çek
            val movieListFromApi = try {
                movieApi.getMoviesByGenre(category, page)
            } catch (e: IOException) {
                emit(Resource.Error(message = "Error updating movies: Network issue"))
                return@flow
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Error updating movies: Server issue"))
                return@flow
            } catch (e: Exception) {
                emit(Resource.Error(message = "Error updating movies: Unknown issue"))
                return@flow
            }

            // Eğer API'den gelen veri, yerel verilerle aynıysa işlem yapma
            val apiMovieEntities = movieListFromApi.results.map { it.toMovieEntity(category) }
            val localMovieEntities = localMovies.map { it.toMovieEntity(category) }

            if (apiMovieEntities == localMovieEntities) {
                emit(Resource.Success(data = localMovies))
                emit(Resource.Loading(false))
                return@flow
            }

            // Yeni veriyi veritabanına kaydet ve UI'ya gönder
            realm.writeBlocking {
                apiMovieEntities.forEach { movieEntity ->
                    copyToRealm(movieEntity)
                }
            }

            val updatedMovies: List<Movie> = realm.query<Movie>("category == $0", category).find()
            emit(Resource.Success(data = updatedMovies))
            emit(Resource.Loading(false))
        }
    }
}
