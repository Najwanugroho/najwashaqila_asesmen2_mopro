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
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.LoginScreen
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.MainScreen
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.RecycleBinScreen
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.RegisterScreen
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.SplashScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.Home.route) {
            MainScreen(navController = navController, isDarkTheme = isDarkTheme)
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(onRegisterSuccess = {
                navController.popBackStack()
            })
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
                navArgument(KEY_ID_CATATAN) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_CATATAN)
            DetailScreen(navController = navController, id = id, isDarkTheme = isDarkTheme)
        }
    }
}
