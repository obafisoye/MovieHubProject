package com.example.moviehubproject.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviehubproject.db.AppDatabase
import com.example.moviehubproject.mvvm.MovieViewModel

@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: MovieViewModel, db: AppDatabase, navController: NavController){
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var query by rememberSaveable{ viewModel.searchTerm }

    Box(
        modifier = Modifier
            .background(color=Color.LightGray)

    ){
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxWidth(),
                text = "Search Screen",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White, // Setting text color
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center // Centering text within the box
            )
            OutlinedTextField(
                value = viewModel.searchTerm.value,
                onValueChange = { viewModel.searchTerm.value = it },
                label = { Text("Search for a movie") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    //viewModel.searchMovies(query, database)
                    keyboardController?.hide()
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(color = Color.White)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(), // Make the Box take up the full available space
                contentAlignment = Alignment.Center // Align content (the Row) to the center
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Button(onClick = {
                        viewModel.searchMovies(query,db)
                        keyboardController?.hide()
                    }) {
                        Text("Search")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(onClick = {
                        query = ""
                        viewModel.searchMovies(query,db)
                        keyboardController?.hide()
                    }) {
                        Text("Clear")
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            for (movie in viewModel.movies.value) {
                LazyColumn{
                    items(viewModel.movies.value){ movie ->
                        MovieCard(movie, navController = navController)
                    }
                }
            }

        }

    }

}
