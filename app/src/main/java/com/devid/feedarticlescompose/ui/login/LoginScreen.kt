package com.devid.feedarticlescompose.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.devid.feedarticlescompose.R
import com.devid.feedarticlescompose.ui.components.MyTextField
import com.devid.feedarticlescompose.ui.components.PrimaryButton
import com.devid.feedarticlescompose.ui.theme.FeedArticlesComposeTheme

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {

        LoginContent(viewModel = viewModel, navController = navController)

}
@Composable
fun LoginContent(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val login by viewModel.login
    val password by viewModel.password
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.login_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTextField(
                value = login,
                label = stringResource(id = R.string.login_hint),
                onValueChange = { viewModel.updateLogin(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyTextField(
                value = password,
                label = stringResource(id = R.string.password_hint),
                isPassword = true,
                onValueChange = { viewModel.updatePassword(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = stringResource(id = R.string.get_login),
                onClick = { viewModel.loginUser() },
                enabled = isButtonEnabled
            )

            Text(
                text = stringResource(id = R.string.register_msg),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .clickable { navController.navigate("register") }
            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigateToMain.collect {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
}