package com.jlrf.mobile.employeepedia.presentation.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.presentation.compose.views.EmployeeItemView
import com.jlrf.mobile.employeepedia.presentation.compose.views.GenericErrorMessageView
import com.jlrf.mobile.employeepedia.presentation.compose.views.ProgressLoaderView
import com.jlrf.mobile.employeepedia.presentation.viewmodels.EmployeesListViewModel

/***************************************************************************************************
 *  Composable Previews
 **************************************************************************************************/

@Preview
@Composable
fun SuccessStatePreview() {
    val state = EmployeesListViewModel.State(
        employees = listOf(
            EmployeeModel(
                id = 1,
                name = "Luis Ramos",
                salary = 150000.00,
                age = 29,
                profileImage = ""
            ),
            EmployeeModel(
                id = 3,
                name = "Jose Fernandez",
                salary = 150000.00,
                age = 29,
                profileImage = ""
            )
        ),
        isLoading = false,
        error = null
    )

    PortraitHomeScreen(
        uiState = state,
        onItemClick = {}
    )
}

@Preview
@Composable
fun LoadingStatePreview() {
    val state = EmployeesListViewModel.State(
        employees = emptyList(),
        isLoading = true,
        error = null
    )

}

@Preview
@Composable
fun ErrorStatePreview() {
    val state = EmployeesListViewModel.State(
        employees = emptyList(),
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
    uiState: EmployeesListViewModel.State,
    onItemClick: (Long) -> Unit = {}
) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            PortraitHomeScreen(
                uiState = uiState,
                onItemClick = onItemClick,
            )
        }

        WindowWidthSizeClass.Expanded -> {
            LandscapeHomeScreen()
        }
    }
}

@Composable
fun PortraitHomeScreen(
    uiState: EmployeesListViewModel.State,
    onItemClick: (Long) -> Unit
) {
    HomeScreenContainer() { paddingValues ->
        HomeScreenContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState, onItemClick = onItemClick
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
    uiState: EmployeesListViewModel.State,
    onItemClick: (Long) -> Unit
) {

    when {
        uiState.isLoading -> {
            ProgressLoaderView()
        }

        uiState.error != null -> {
            GenericErrorMessageView()
        }

        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
            ) {
                items(uiState.employees) {
                    EmployeeItemView(
                        model = it,
                        onClick = onItemClick
                    )
                }
            }
        }
    }
}