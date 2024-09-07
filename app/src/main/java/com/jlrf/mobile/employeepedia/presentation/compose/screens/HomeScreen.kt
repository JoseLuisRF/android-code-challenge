package com.jlrf.mobile.employeepedia.presentation.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.presentation.compose.views.GenericErrorMessageView
import com.jlrf.mobile.employeepedia.presentation.compose.views.LoadingListItemView
import com.jlrf.mobile.employeepedia.presentation.compose.views.MovieItemView
import com.jlrf.mobile.employeepedia.presentation.compose.views.ProgressLoaderView
import com.jlrf.mobile.employeepedia.presentation.viewmodels.MoviesViewModel

/***************************************************************************************************
 *  Composable Previews
 **************************************************************************************************/

@Preview
@Composable
fun SuccessStatePreview() {
    val state = MoviesViewModel.State(
        isLoading = false,
        error = null
    )
}

@Preview
@Composable
fun LoadingStatePreview() {
    val state = MoviesViewModel.State(
        isLoading = true,
        error = null
    )

}

@Preview
@Composable
fun ErrorStatePreview() {
    val state = MoviesViewModel.State(
        isLoading = false,
        error = Error()
    )

}

/***************************************************************************************************
 *  Composable Views
 **************************************************************************************************/

@Composable
fun HomeScreen(
    windowSize: WindowSizeClass,
    uiState: MoviesViewModel.State,
    pagingData: LazyPagingItems<MovieModel>,
    onItemClick: (MovieModel) -> Unit = {}
) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            PortraitHomeScreen(
                uiState = uiState,
                onItemClick = onItemClick,
                pagingData = pagingData,
            )
        }

        WindowWidthSizeClass.Expanded -> {
            LandscapeHomeScreen()
        }
    }
}

@Composable
fun PortraitHomeScreen(
    uiState: MoviesViewModel.State,
    pagingData: LazyPagingItems<MovieModel>,
    onItemClick: (MovieModel) -> Unit
) {

    HomeScreenContainer() { paddingValues ->
        HomeScreenContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            pagingData = pagingData,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun LandscapeHomeScreen() {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContainer(
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
                        text = stringResource(R.string.employees_fragment_label),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        }
    ) { paddingValues ->
        contentView(paddingValues)
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    uiState: MoviesViewModel.State,
    pagingData: LazyPagingItems<MovieModel>,
    onItemClick: (MovieModel) -> Unit
) {

    when {
        uiState.isLoading -> {
            ProgressLoaderView()
        }

        uiState.error != null -> {
            GenericErrorMessageView()
        }

        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
            ) {
                items(pagingData.itemCount) { index ->
                    pagingData[index]?.let { model ->
                        MovieItemView(
                            model = model,
                            onClick = onItemClick
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