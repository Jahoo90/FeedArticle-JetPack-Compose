package com.devid.feedarticlescompose.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devid.feedarticlescompose.R
import com.devid.feedarticlescompose.ui.components.MyTextField
import com.devid.feedarticlescompose.ui.components.PrimaryButton
import com.devid.feedarticlescompose.ui.theme.FeedArticlesComposeTheme


@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel, navController: NavHostController) {


    LaunchedEffect(key1 = registerViewModel.navigateToMain) {
        registerViewModel.navigateToMain.collect {
            navController.navigate("main"){
                popUpTo("register") { inclusive = true }
            }
        }
    }

    RegisterContent(registerViewModel)
}

@Composable
fun RegisterContent(registerViewModel: RegisterViewModel) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.register_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTextField(
                value = registerViewModel.login.collectAsState().value,
                label = "Login",
                onValueChange = { registerViewModel.updateLogin(it) },
                singleLine = true,
                maxLength = 80
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyTextField(
                value = registerViewModel.password.collectAsState().value,
                label = "Password",
                onValueChange = { registerViewModel.updatePassword(it) },
                isPassword = true,
                singleLine = true,
                maxLength = 80
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyTextField(
                value = registerViewModel.confirmPassword.collectAsState().value,
                label = "Confirmation Password",
                onValueChange = { registerViewModel.updateConfirmPassword(it) },
                isPassword = true,
                singleLine = true,
                maxLength = 80
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = stringResource(id = R.string.get_register),
                onClick = { registerViewModel.registerUser() },
                enabled = registerViewModel.isButtonEnabled.collectAsState().value
            )

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

/*
@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel, navController: NavHostController) {

    RegisterContent()
}

@Composable
fun RegisterContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.register_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTextField("", "Login", {})

            Spacer(modifier = Modifier.height(16.dp))

            MyTextField("", "Password", {})

            Spacer(modifier = Modifier.height(16.dp))

            MyTextField("", "Confirmation Password", {})

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = stringResource(id = R.string.get_register),
                onClick = { /* Add registration logic here */ }
            )

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}*/

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    FeedArticlesComposeTheme {
//        RegisterScreen()
    }
}