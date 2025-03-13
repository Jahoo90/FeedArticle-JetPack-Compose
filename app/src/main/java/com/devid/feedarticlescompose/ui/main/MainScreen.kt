package com.devid.feedarticlescompose.ui.main


import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.devid.feedarticlescompose.R
import com.devid.feedarticlescompose.network.dtos.ArticlesResponseItem
import com.devid.feedarticlescompose.ui.theme.Green
import com.devid.feedarticlescompose.ui.theme.LightGreenr
import com.devid.feedarticlescompose.ui.theme.LightPrimaryColor
import com.devid.feedarticlescompose.ui.theme.LightRed
import com.devid.feedarticlescompose.ui.theme.PrimaryColor
import com.devid.feedarticlescompose.ui.theme.Red
import com.devid.feedarticlescompose.ui.theme.VeryLightGray
import com.devid.feedarticlescompose.ui.theme.VeryLightGreenr
import com.devid.feedarticlescompose.ui.theme.VeryLightPrimaryColor
import com.devid.feedarticlescompose.ui.theme.VeryLightRed
import kotlinx.coroutines.delay

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    MainContent(navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(navController: NavController, mainViewModel: MainViewModel) {


    /*val articles by mainViewModel.articles.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
//    val allArticles = articles.toList()
    val allArticles: List<ArticlesResponseItem> = articles
//    var filteredArticles = mainViewModel.getFilteredArticles()*/

    val articles by mainViewModel.articles.collectAsState()
    val selectedCategory by mainViewModel.selectedCategory.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(mainViewModel) {
        mainViewModel.errorMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        topBar = { MainTopAppBar(navController, mainViewModel) },
        bottomBar = { BottomNavigationBar(mainViewModel) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
//                items(articles) { article -> // Używamy przefiltrowanej listy
//                    ArticleItem(
//                        title = article.titre,
//                        description = article.descriptif,
//                        imageUrl = article.urlImage,
//                        category = article.categorie
//                    )
//                }
                items(articles.size) { index ->
                    ArticleItem(
                        title = articles[index].titre,
                        description = articles[index].descriptif,
                        imageUrl = articles[index].urlImage,
                        category = articles[index].categorie
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(navController: NavController, mainViewModel: MainViewModel) {
    SmallTopAppBar(
        title = { Text("") },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = VeryLightGray),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("create")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add article")
            }
        },
        actions = {
            IconButton(onClick = {
                mainViewModel.logout()
                navController.navigate("login") {
                    popUpTo("main") { inclusive = true }
                }
            }) {
                Icon(Icons.Default.PowerSettingsNew, contentDescription = "Logout")
            }
        }
    )
}

@Composable
fun BottomNavigationBar(mainViewModel: MainViewModel) {

    val selectedCategory by mainViewModel.selectedCategory.collectAsState()
    val categories = listOf("Tout", "Sport", "Manga", "Divers")

    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            categories.forEachIndexed { index, text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable { mainViewModel.filterArticlesByCategory(index) }
                ) {
                    RadioButton(
                        selected = selectedCategory == index,
                        onClick = {
                            mainViewModel.filterArticlesByCategory(index)
                            Log.i("jahoo", "index is : $index")
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = when (index) {
                                1 -> Green
                                2 -> Red
                                else -> PrimaryColor
                            },
                            unselectedColor = when (index) {
                            1 -> LightGreenr
                            2 -> LightRed
                            else -> LightPrimaryColor
                        }
                        )
                    )
                    Text(text = text)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleItem(
    title: String,
    description: String,
    imageUrl: String?,
    category: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background( when (category) {
                    1 -> VeryLightGreenr
                    2 -> VeryLightRed
                    else -> VeryLightPrimaryColor
                }),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .error(R.drawable.feedarticleslogo)
                    .placeholder(R.drawable.feedarticleslogo)
                    .build(),
                contentDescription = "Article Image",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape),
                contentScale = ContentScale.Crop
            )


            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
        }
    }
}