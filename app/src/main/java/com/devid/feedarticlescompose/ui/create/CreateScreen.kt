package com.devid.feedarticlescompose.ui.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.devid.feedarticlescompose.R
import com.devid.feedarticlescompose.ui.components.MyTextField
import com.devid.feedarticlescompose.ui.components.PrimaryButton
import com.devid.feedarticlescompose.ui.theme.PrimaryColor

@Composable
fun CreateScreen(navController: NavHostController, createViewModel: CreateViewModel) {

    CreateContent(createViewModel, navController)
}

@Composable
fun CreateContent(createViewModel: CreateViewModel, navController: NavHostController){

    var url by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    val onFocusChange: (Boolean) -> Unit = { focused ->
        if (!focused && url.isNotEmpty()) {
            imageUrl = url
        }
        isFocused = focused
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = stringResource(id = R.string.create_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryColor
        )
        MyTextField(
            value = createViewModel.title,
            label = stringResource(id = R.string.create_titre_hint),
            onValueChange = { createViewModel.onTitleChange(it) },
            maxLines = 3,
            maxLength = 80
        )

        MyTextField(
            value = createViewModel.description,
            label = stringResource(id = R.string.create_descriptif_hint),
            onValueChange = { createViewModel.onDescriptionChange(it) },
            minLines = 5,
            maxLines = 8,
            modifier = Modifier
                .fillMaxWidth()
        )

        MyTextField(
            value = createViewModel.imageUrl,
            label = stringResource(id = R.string.create_image_hint),
            onValueChange = { createViewModel.onImageUrlChange(it) },
            modifier = Modifier
                .onFocusChanged { onFocusChange(it.isFocused) }
                .verticalScroll(rememberScrollState())
        )


        if (createViewModel.imageUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(createViewModel.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .width(160.dp)
                    .padding(vertical = 10.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.feedarticleslogo),
                contentDescription = null,
                modifier = Modifier
                    .width(160.dp)
                    .padding(vertical = 10.dp)
            )
        }

//        ************************* Radio Button *************************
        var selectedOption by remember { mutableStateOf("Divers") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            val categories = listOf("Sport" to 1, "Manga" to 2, "Divers" to 3)

            categories.forEach { (label, value) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = createViewModel.selectedCategory == value,
                        onClick = { createViewModel.onCategoryChange(value) }
                    )
                    Text(text = label)
                }
            }
        }

        val context = LocalContext.current
        PrimaryButton(
            text = stringResource(id = R.string.create_button),
            onClick = { createViewModel.createArticle(context)
            navController.navigate("main") {
                popUpTo("create") { inclusive = true }
            }}
        )
    }
}
//
//@Composable
//@Preview (showBackground = true)
//fun PreviewCreateScreen() {
//    CreateContent()
//}