package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.MainScreen
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.ui.theme.Asesment2mopro_najwashaqilaisnanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Asesment2mopro_najwashaqilaisnanTheme {
               MainScreen()
                }
            }
        }
    }
