package com.example.moviehubproject.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.moviehubproject.api.model.Movie
import com.example.moviehubproject.api.model.MovieData
import com.example.moviehubproject.db.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesManager(database: AppDatabase) {
    private var _moviesResponse = mutableStateOf<List<Movie>>(emptyList())
    val api_key = "35a6f3b99f57f1ea0096848194401520"

    val moviesResponse: MutableState<List<Movie>>
        @Composable get() = remember {
            _moviesResponse
        }

    init{
        getMovies(database)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getMovies(database: AppDatabase){
        val service = Api.retrofitService.getTrendingMovies(api_key)
        
        service.enqueue(object : Callback<MovieData> {
            override fun onResponse(
                call: Call<MovieData>,
                response: Response<MovieData>
            ){
                if (response.isSuccessful){
                    Log.i("Data", "Data is loaded")
                    _moviesResponse.value = response.body()?.results?: emptyList()
                    Log.i("DataStream", _moviesResponse.value.toString())

                    // save data to database
                    GlobalScope.launch {
                        saveDataToDatabase(database = database, _moviesResponse.value)
                    }
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d("error", "${t.message}")
            }

        })
    }
    private suspend fun saveDataToDatabase(database: AppDatabase, movies: List<Movie>){
        database.movieDao().insertAllMovies(movies)
    }
}