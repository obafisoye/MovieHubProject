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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviehubproject.destinations.Destination
import com.example.moviehubproject.screens.MovieDetailScreen
import com.example.moviehubproject.screens.MovieScreen
import com.example.moviehubproject.screens.WatchLaterScreen
import com.example.moviehubproject.ui.theme.MovieHubProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    //MovieScreen(modifier = Modifier.padding(innerPadding))
                    App(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController, modifier: Modifier){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MovieHub")}
            )
        }
        //bottomBar = (BottomNavbar(navController = navController))
    ){
        paddingValues ->
            paddingValues.calculateBottomPadding()
        Spacer(modifier = Modifier.padding(10.dp))

        NavHost(navController = navController, startDestination = Destination.Movie.route){
            composable(Destination.Movie.route){
                MovieScreen(modifier = Modifier.padding(paddingValues))
            }
            composable(Destination.WatchLater.route){
                WatchLaterScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}
