package com.example.moviehubproject.view.Navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviehubproject.R

@Composable
fun ButtomNavBar(navController: NavController){
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination
        
        val ic_movie = painterResource(id = R.drawable.ic_movie)
        val ic_search = painterResource(id = R.drawable.ic_search)
        val ic_watch = painterResource(id = R.drawable.ic_watch)
    }
}