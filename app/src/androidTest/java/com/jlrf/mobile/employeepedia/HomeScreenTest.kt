package com.jlrf.mobile.employeepedia

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.presentation.compose.screens.HomeScreen
import com.jlrf.mobile.employeepedia.presentation.viewmodels.MoviesViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun homeScreen_displaysMovies_whenLoadingIsSuccess() {
        val fakeMovies = listOf(
            MovieModel(
                genreIds = emptyList(),
                id = 1,
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "Overview",
                popularity = 1.0,
                posterPath = "posterPath",
                releaseDate = "2024-07-02",
                title = "Movie 1",
                backdropPath = "backdropPath",
            ),
            MovieModel(
                genreIds = emptyList(),
                id = 2,
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "Overview",
                popularity = 1.0,
                posterPath = "posterPath",
                releaseDate = "2023-07-02",
                title = "Movie 2",
                backdropPath = "backdropPath",
            )
        )

        composeTestRule.setContent {
            val fakePagingData = flowOf(PagingData.from(fakeMovies)).collectAsLazyPagingItems()

            HomeScreen(
                windowSize = WindowWidthSizeClass.Compact,
                uiState = MoviesViewModel.State(
                    isLoading = false,
                    error = null
                ),
                pagingData = fakePagingData,
                onItemClick = {}
            )
        }

        // Assert: Verify displayed items
        composeTestRule.onNode(hasContentDescription("Movie 1")).assertIsDisplayed()
        composeTestRule.onNode(hasContentDescription("Movie 2")).assertIsDisplayed()

    }
}