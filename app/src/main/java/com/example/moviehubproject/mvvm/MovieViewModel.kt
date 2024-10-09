package com.example.moviehubproject.mvvm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.moviehubproject.api.Api
import com.example.moviehubproject.api.model.Movie
import com.example.moviehubproject.api.model.MovieData
import com.example.moviehubproject.db.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieViewModel : ViewModel(){
    val api_key: String = "35a6f3b99f57f1ea0096848194401520"
    val movies = mutableStateOf<List<Movie>>(emptyList())

    var searchTerm = mutableStateOf("")
    //val moviesResponse:

    fun searchMovies(movieName: String, database: AppDatabase){

        if (movieName.isNotBlank()){
            // api call
            val service = Api.retrofitService.searchMovieByName(api_key, movieName )
            service.enqueue(object: Callback<MovieData>{

                override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                    if(response.isSuccessful){
                        Log.i("SearchData", "testing")

                        movies.value = response.body()?.results?: emptyList()
                        GlobalScope.launch {
                            database.movieDao().insertAllMovies(movies.value)
                        }
                    }
                }
                override fun onFailure(call: Call<MovieData>, t: Throwable) {
                    Log.d("error", "${t.message}")

                }
            })
        }
        else{
            movies.value = emptyList()
        }
    }

    fun saveSearchTerm(term: String){
        searchTerm.value = term
    }
}