package com.example.moviehubproject.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviehubproject.api.MoviesManager
import com.example.moviehubproject.api.model.Movie

@Composable
fun MovieScreen(modifier: Modifier = Modifier, moviesManager: MoviesManager, navController: NavController, context: Context){


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ){
        Text(
            text = "Movie Screen",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = {
                val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                sharedPref.edit()
                    .putBoolean("isLoggedIn", false)
                    .apply()

                Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
                Log.d("sharedpref", "${sharedPref.getString("email", "")}, ${sharedPref.getString("password", "")}, ${sharedPref.getBoolean("isLoggedIn", false)}")
            }
        ) {
            Text(text = "Log Out")
        }

        val movies = moviesManager.moviesResponse.value

        LazyColumn {
//            items(movies){movie ->
//                MovieCard(movieItem = movie, navController = navController)
//            }
        }
    }

}

@Composable
fun MovieCard(
    movieItem: Movie,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Red, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                Log.i("MovieCard", "Clicked ${movieItem.title}")
                navController.navigate("movieDetail/${movieItem.id}")
            }
    ) {
        Row(
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data("https://image.tmdb.org/t/p/w500${movieItem.poster_path}")
                    .build(),
                contentDescription = movieItem.overview,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}