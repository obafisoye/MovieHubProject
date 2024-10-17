package com.example.moviehubproject

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviehubproject.api.MoviesManager
import com.example.moviehubproject.api.model.Movie
import com.example.moviehubproject.db.AppDatabase
import com.example.moviehubproject.destinations.Destination
import com.example.moviehubproject.mvvm.MovieViewModel
import com.example.moviehubproject.screens.MovieDetailScreen
import com.example.moviehubproject.screens.MovieScreen
import com.example.moviehubproject.screens.SearchScreen
import com.example.moviehubproject.screens.WatchLaterScreen
import com.example.moviehubproject.ui.theme.MovieHubProjectTheme
import com.example.moviehubproject.view.navigation.BottomNavBar
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

                    // view model
                    val viewModel: MovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

                    // firestore db
                    val fs_db = Firebase.firestore

                    //MovieScreen(modifier = Modifier.padding(innerPadding))
                    App(navController = navController, modifier = Modifier.padding(innerPadding), moviesManager, db, viewModel, fs_db)
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController, modifier: Modifier, moviesManager: MoviesManager, db: AppDatabase, viewModel: MovieViewModel, fs_db: FirebaseFirestore){
    var movie by remember{
        mutableStateOf<Movie?>(null)
    }
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
                WatchLaterScreen(modifier = Modifier.padding(paddingValues), navController)
            }
            composable(Destination.Search.route){
                SearchScreen(modifier = Modifier.padding(paddingValues), viewModel = viewModel, db = db, navController)
            }
            composable(Destination.MovieDetail.route){ navBackStackEntry ->
                val movie_id: String? = navBackStackEntry.arguments?.getString("movieId")

                GlobalScope.launch {
                    if(movie_id != null){
                        movie = db.movieDao().getMovieById(movie_id.toInt())
                    }
                }

                movie?.let { MovieDetailScreen(modifier = Modifier.padding(paddingValues), movie = it, db = db, viewModel = viewModel, fs_db = fs_db) }
            }
        }
    }
}
