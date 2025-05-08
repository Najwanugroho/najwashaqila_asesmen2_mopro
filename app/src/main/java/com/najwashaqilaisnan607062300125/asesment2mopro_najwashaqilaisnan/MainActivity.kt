package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.navigation.SetupNavGraph
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.ui.theme.Asesment2mopro_najwashaqilaisnanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val isDarkTheme = rememberSaveable { mutableStateOf(false) }

            Asesment2mopro_najwashaqilaisnanTheme(darkTheme = isDarkTheme.value) {
               SetupNavGraph(navController, isDarkTheme)
                }
            }
        }
    }
