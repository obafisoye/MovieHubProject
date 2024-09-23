package com.example.moviehubproject.destinations

/*
 This class is responsible for where the app should navigate to.
 By using sealed classes, we are not able to further subclass this class.
 */

sealed class Destination(val route: String){
    object Movie: Destination("movie")
    object Search: Destination("search")
    object Favoriite: Destination("favorite")
    object MovieDetail: Destination("movieDetail/{movieId}"){
        fun createRoute(movieId: Int?) = "movieDetail/$movieId"
    }
}