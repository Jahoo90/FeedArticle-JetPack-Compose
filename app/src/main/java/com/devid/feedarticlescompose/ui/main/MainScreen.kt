package com.devid.feedarticlescompose.ui.main


import android.R.attr.minLines
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.util.Collections.rotate

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {

    val articles by viewModel.articles.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.errorMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }
    MainContent(
        navController = navController,
        articles = articles,
        snackbarHostState = snackbarHostState,
        selectedCategory = selectedCategory,
        onCategorySelected = { index -> viewModel.filterArticlesByCategory(index) },
        onLogout = {
            viewModel.logout()
            navController.navigate("login") {
                popUpTo("main") { inclusive = true }
            }
        },
        onRefresh = { viewModel.fetchArticles() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavController,
    articles: List<ArticlesResponseItem>,
    snackbarHostState: SnackbarHostState,
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit,
    onLogout: () -> Unit,
    onRefresh: () -> Unit
) {
    val state = rememberPullToRefreshState()
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = { MainTopAppBar(navController, onLogout) },
        bottomBar = { BottomNavigationBar(selectedCategory, onCategorySelected) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    isRefreshing = true
                    onRefresh()
                    isRefreshing = false} ,
                state = state,
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter).rotate(360f),
                        isRefreshing = isRefreshing,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        state = state,
                    )
                },
            )
            {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(articles.size) { index ->
                        val article = articles[index]
                        ArticleItem(
                            title = article.titre,
                            description = article.descriptif,
                            imageUrl = article.urlImage,
                            category = article.categorie,
//                            date = article.createdAt,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(navController: NavController, onLogout: () -> Unit) {
    TopAppBar(
        title = { Text("") },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = VeryLightGray),
        navigationIcon = {
            IconButton(onClick = { navController.navigate("create") }) {
                Icon(Icons.Default.Add, contentDescription = "Add article")
            }
        },
        actions = {
            IconButton(onClick = onLogout) {
                Icon(Icons.Default.PowerSettingsNew, contentDescription = "Logout")
            }
        }
    )
}

@Composable
fun BottomNavigationBar(selectedCategory: Int, onCategorySelected: (Int) -> Unit) {
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
                        .clickable { onCategorySelected(index) }
                ) {
                    RadioButton(
                        selected = selectedCategory == index,
                        onClick = { onCategorySelected(index) },
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
                .background(
                    when (category) {
                        1 -> VeryLightGreenr
                        2 -> VeryLightRed
                        else -> VeryLightPrimaryColor
                    }
                ),
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


/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClickedArticleItem(
    title: String,
    description: String,
    imageUrl: String?,
    category: Int,
    date: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    when (category) {
                        1 -> VeryLightGreenr
                        2 -> VeryLightRed
                        else -> VeryLightPrimaryColor
                    }
                )
                .padding(10.dp),

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(

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
                        .width(80.dp)
                        .height(80.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.LightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(22.dp)
                )
            }



            Column(modifier = Modifier.padding(16.dp)) {

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = date,
                        fontSize = 14.sp,
                        color = Color.Black,
                    )
                    Text(
                        text = when (category) {
                            1 -> stringResource(R.string.sport)
                            2 -> stringResource(R.string.manga)
                            else -> stringResource(R.string.divers)
                        },

                        fontSize = 14.sp,
                        color = Color.Black,
                    )
                }
                Column {
                    Text(
                        text = description,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

*/