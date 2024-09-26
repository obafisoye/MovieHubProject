package com.example.moviehubproject.view.Navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviehubproject.R
import com.example.moviehubproject.destinations.Destination

@Composable
fun BottomNavBar(navController: NavController){
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination
        
        val ic_movie = painterResource(id = R.drawable.ic_movie)
        val ic_search = painterResource(id = R.drawable.ic_search)
        val ic_watch = painterResource(id = R.drawable.ic_watch)

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Movie.route,
            onClick = { navController.navigate(Destination.Movie.route){
                popUpTo(Destination.Movie.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = ic_movie, contentDescription = "Home Movie Screen Button") },
            label = { Text(text = Destination.Movie.route)}
        )

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Search.route,
            onClick = { navController.navigate(Destination.Search.route){
                popUpTo(Destination.Search.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = ic_search, contentDescription = "Search Screen Button") },
            label = { Text(text = Destination.Search.route)}
        )

        NavigationBarItem(
            selected = currentDestination?.route == Destination.WatchLater.route,
            onClick = { navController.navigate(Destination.WatchLater.route){
                popUpTo(Destination.WatchLater.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = ic_watch, contentDescription = "Watch Later Screen Button") },
            label = { Text(text = Destination.WatchLater.route)}
        )
    }
}