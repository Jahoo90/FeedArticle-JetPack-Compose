package com.devid.feedarticlescompose


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devid.feedarticlescompose.ui.create.CreateScreen
import com.devid.feedarticlescompose.ui.create.CreateViewModel
import com.devid.feedarticlescompose.ui.login.LoginScreen
import com.devid.feedarticlescompose.ui.login.LoginViewModel
import com.devid.feedarticlescompose.ui.main.MainScreen
import com.devid.feedarticlescompose.ui.main.MainViewModel
import com.devid.feedarticlescompose.ui.register.RegisterScreen
import com.devid.feedarticlescompose.ui.register.RegisterViewModel
import com.devid.feedarticlescompose.ui.splash.SplashScreen
import com.devid.feedarticlescompose.ui.splash.SplashViewModel

sealed class Screen(val route: String) {
    object Splash   : Screen("splash")
    object Main     : Screen("main")
    object Login    : Screen("login")
    object Register : Screen("register")
    object Create   : Screen("create")
    object Edit     : Screen("edit")
}


@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            val splashViewModel: SplashViewModel = hiltViewModel()
            SplashScreen(navController = navController, viewModel = splashViewModel)
        }
        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController = navController, viewModel = loginViewModel)
        }
        composable(Screen.Main.route) {
            val mainViewModel: MainViewModel = hiltViewModel()
            MainScreen(navController = navController, viewModel = mainViewModel)
        }
        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(navController = navController, registerViewModel = registerViewModel)
        }
        composable(Screen.Create.route) {
            val createViewModel: CreateViewModel = hiltViewModel()
            CreateScreen(navController = navController, createViewModel = createViewModel)
        }
    }
}