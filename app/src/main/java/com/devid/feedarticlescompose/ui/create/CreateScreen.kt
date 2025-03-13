package com.devid.feedarticlescompose.ui.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CreateScreen(navController: NavHostController, createViewModel: CreateViewModel) {

}


@Composable
fun CreateContent(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.create_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        MyTextField(
            value = "",
            label = stringResource(id = R.string.create_titre_hint),
            onValueChange = {}
        )

    }

}

@Composable
@Preview (showBackground = true)
fun PreviewCreateScreen() {
    CreateContent()
}