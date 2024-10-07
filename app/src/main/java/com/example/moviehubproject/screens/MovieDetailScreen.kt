package com.example.moviehubproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.moviehubproject.api.model.Movie
import com.example.moviehubproject.db.AppDatabase


@Composable
fun MovieDetailScreen(modifier: Modifier, movie: Movie, db: AppDatabase){

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
    ){
        Column {
            Text(
                text = movie.title!!,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                //modifier = Modifier.align(Alignment.Center)

            )
            Text(
                text = movie.overview!!,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                //modifier = Modifier.align(Alignment.Center)

            )
            Text(
                text = movie.poster_path!!,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                //modifier = Modifier.align(Alignment.Center)

            )
        }

    }
}