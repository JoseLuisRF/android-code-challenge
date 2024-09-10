package com.jlrf.mobile.employeepedia.presentation.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.models.MovieReviewModel
import com.jlrf.mobile.employeepedia.presentation.compose.views.GenericErrorMessageView
import com.jlrf.mobile.employeepedia.presentation.compose.views.LoadingListItemView
import com.jlrf.mobile.employeepedia.presentation.compose.views.MovieReviewItemView
import com.jlrf.mobile.employeepedia.presentation.compose.views.ProgressLoaderView
import com.jlrf.mobile.employeepedia.presentation.viewmodels.MovieDetailsViewModel
import com.jlrf.mobile.employeepedia.presentation.viewmodels.MoviesViewModel

/***************************************************************************************************
 *  Composable Previews
 **************************************************************************************************/

@Preview
@Composable
fun PortraitEmployeeDetailsScreenPreview() {
    val uiState = MovieDetailsViewModel.State(
        movie = MovieModel(
            genreIds = emptyList(),
            id = 1,
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Overview",
            popularity = 1.0,
            posterPath = "posterPath",
            releaseDate = "releaseDate",
            title = "Title",
            backdropPath = "backdropPath",
        ),
        isLoading = false,
        error = null
    )
}

/***************************************************************************************************
 *  Composable Views
 **************************************************************************************************/

@Composable
fun MovieDetailsScreen(
    widthSizeClass: WindowWidthSizeClass,
    mainUiState: MoviesViewModel.State,
    onBackPressed: () -> Unit,
) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    mainUiState.selectedMovie?.let { model ->
                        viewModel.loadMovieReviews(model)
                    }
                }

                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    val uiState by viewModel.state.collectAsState()

    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            PortraitEmployeeDetailsScreen(
                uiState = uiState,
                pagingData = pagingData,
                onBackPressed = onBackPressed
            )
        }

        WindowWidthSizeClass.Expanded -> {
            LandscapeEmployeeDetailsScreen()
        }
    }
}

@Composable
fun PortraitEmployeeDetailsScreen(
    uiState: MovieDetailsViewModel.State,
    pagingData: LazyPagingItems<MovieReviewModel>,
    onBackPressed: () -> Unit = {},
) {
    EmployeeDetailsContainer(
        onBackPressed = onBackPressed
    ) { paddingValues ->
        EmployeeDetailsContent(
            modifier = Modifier.padding(paddingValues = paddingValues),
            pagingData = pagingData,
            uiState = uiState
        )
    }
}

@Composable
fun LandscapeEmployeeDetailsScreen() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDetailsContainer(
    onBackPressed: () -> Unit,
    contentView: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.employees_details_fragment_label),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Button",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        contentView(paddingValues)
    }

}

@Composable
fun EmployeeDetailsContent(
    modifier: Modifier = Modifier,
    pagingData: LazyPagingItems<MovieReviewModel>,
    uiState: MovieDetailsViewModel.State,
) {
    when {
        uiState.isLoading -> {
            ProgressLoaderView()
        }

        uiState.error != null -> {
            GenericErrorMessageView()
        }

        else -> {
            val model = uiState.movie
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)

                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model?.backdropPath),
                                contentDescription = model?.title.orEmpty(),
                                modifier = Modifier
                                    .height(300.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop,
                            )
                            Text(
                                text = model?.title.orEmpty(),
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.black),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(top = 8.dp)
                            )
                            Text(
                                modifier = Modifier.padding(
                                    top = 8.dp
                                ),
                                text = "Overview",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(id = R.color.black)
                            )
                            Text(
                                text = model?.overview.orEmpty(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = colorResource(id = R.color.black),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(top = 10.dp)
                            )
                            Text(
                                modifier = Modifier.padding(
                                    top = 16.dp
                                ),
                                text = "Reviews",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(id = R.color.black)
                            )
                        }
                    }

                    items(pagingData.itemCount) { index ->
                        pagingData[index]?.let {
                            MovieReviewItemView(
                                model = it
                            )
                        }
                    }
                }
                pagingData.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> { // Initial load
                            LoadingListItemView()
                        }

                        loadState.append is LoadState.Loading -> { // Load more
                            LoadingListItemView()
                        }

                        loadState.refresh is LoadState.Error -> { // Error on initial load
                            // Retry button
                        }

                        loadState.append is LoadState.Error -> { // Error on load more
                            // Show Retry
                        }
                    }
                }

            }
        }
    }
}

