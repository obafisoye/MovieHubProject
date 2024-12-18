package com.example.moviehubproject.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.moviehubproject.MainActivity
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignInScreen(context: Context, modifier: Modifier) {

    // state level variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Email") },
            keyboardOptions =  KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Password") },
            keyboardOptions =  KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = {
                performSignIn(email, password, context, keyboardController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text("Sign In")
        }
    }
}

fun performSignIn(email: String, password: String, context: Context, keyboardController: SoftwareKeyboardController?){

    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener{ task ->
            if (task.isSuccessful) {

                // save email and password to shared preferences
                val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                sharedPref.edit()
                    .putString("email", email)
                    .putString("password", password)
                    .putBoolean("isLoggedIn", true)
                    .apply()

                Log.d("sharedpref", "${sharedPref.getString("email", "")}, ${sharedPref.getString("password", "")}, ${sharedPref.getBoolean("isLoggedIn", false)}")

                Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()

                var intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("userId", auth.currentUser?.uid)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Sign In Failed", Toast.LENGTH_SHORT).show()
            }
            keyboardController?.hide()
        }
}
