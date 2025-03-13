package com.devid.feedarticlescompose.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devid.feedarticlescompose.R
import com.devid.feedarticlescompose.ui.theme.PrimaryColor
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavHostController, viewModel: SplashViewModel) {

    SplashContent()

    LaunchedEffect(true) {
        launch {
            viewModel.goToMainScreenSharedFlow.collect {
                navController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        launch {
            viewModel.goToLoginScreenSharedFlow.collect {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun SplashContent(){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.feedarticleslogo),
            contentDescription = "Feed Articles Logo",
            modifier = Modifier.size(250.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    }
}