package com.example.moviehubproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviehubproject.api.MoviesManager
import com.example.moviehubproject.api.model.Movie
import com.example.moviehubproject.db.AppDatabase
import com.example.moviehubproject.destinations.Destination
import com.example.moviehubproject.screens.MovieDetailScreen
import com.example.moviehubproject.screens.MovieScreen
import com.example.moviehubproject.screens.SearchScreen
import com.example.moviehubproject.screens.WatchLaterScreen
import com.example.moviehubproject.ui.theme.MovieHubProjectTheme
import com.example.moviehubproject.view.navigation.BottomNavBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    // get db instance
                    val db = AppDatabase.getInstance(applicationContext)

                    // fetch data
                    val moviesManager = MoviesManager(db)

                    //MovieScreen(modifier = Modifier.padding(innerPadding))
                    App(navController = navController, modifier = Modifier.padding(innerPadding), moviesManager, db)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController, modifier: Modifier, moviesManager: MoviesManager, db: AppDatabase){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MovieHub")}
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ){
        paddingValues ->
        paddingValues.calculateBottomPadding()
        Spacer(modifier = Modifier.padding(10.dp))

        NavHost(navController = navController,
            startDestination = Destination.Movie.route){

            composable(Destination.Movie.route){
                MovieScreen(modifier = Modifier.padding(paddingValues), moviesManager, navController)
            }
            composable(Destination.WatchLater.route){
                WatchLaterScreen(modifier = Modifier.padding(paddingValues))
            }
            composable(Destination.Search.route){
                SearchScreen(modifier = Modifier.padding(paddingValues))
            }
            composable(Destination.MovieDetail.route){
                val movie = Movie(title = "Fake Movie", overview = "This is a fake movie", poster_path = "fake.jpg")
                MovieDetailScreen(modifier = Modifier.padding(paddingValues), movie = movie, db = db)
            }
        }
    }
}
