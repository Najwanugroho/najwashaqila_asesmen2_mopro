package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.DetailScreen
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.KEY_ID_CATATAN
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.MainScreen
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.RecycleBinScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController(), isDarkTheme: MutableState<Boolean>){
    NavHost(
        navController=navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route){
            MainScreen(navController = navController, isDarkTheme = isDarkTheme)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController = navController, isDarkTheme = isDarkTheme)
        }
        composable(route = Screen.RecycleBin.route) {
            RecycleBinScreen(navController)
        }

        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_CATATAN){type =NavType.LongType}
            )
        ){navBackStackEntry ->
            val id =navBackStackEntry.arguments?.getLong(KEY_ID_CATATAN)
            DetailScreen(navController = navController, isDarkTheme = isDarkTheme)
        }
    }
}