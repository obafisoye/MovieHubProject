package com.example.moviehubproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moviehubproject.api.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): Movie?

    @Update
    fun updateMovie(movie: Movie)
}