package com.jlrf.mobile.employeepedia.presentation.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.presentation.compose.views.GenericErrorMessageView
import com.jlrf.mobile.employeepedia.presentation.compose.views.ProgressLoaderView
import com.jlrf.mobile.employeepedia.presentation.viewmodels.EmployeeDetailsViewModel

/***************************************************************************************************
 *  Composable Previews
 **************************************************************************************************/
@Preview
@Composable
fun PortraitEmployeeDetailsScreenPreview() {
    val uiState = EmployeeDetailsViewModel.State(
        employee = EmployeeModel(
            id = 1,
            name = "Luis Ramos",
            salary = 150000.00,
            age = 29,
            profileImage = ""
        ),
        isLoading = false,
        error = null
    )
    PortraitEmployeeDetailsScreen(uiState = uiState)
}

/***************************************************************************************************
 *  Composable Views
 **************************************************************************************************/

@Composable
fun EmployeeDetailsScreen(
    windowSize: WindowSizeClass,
    employeeId: Long,
    onBackPressed: () -> Unit,
) {
    val viewModel = hiltViewModel<EmployeeDetailsViewModel>()
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.selectEmployee(employeeId)
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

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            PortraitEmployeeDetailsScreen(
                uiState = uiState,
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
    uiState: EmployeeDetailsViewModel.State,
    onBackPressed: () -> Unit = {},
) {
    EmployeeDetailsContainer(
        onBackPressed = onBackPressed
    ) { paddingValues ->
        EmployeeDetailsContent(
            modifier = Modifier.padding(paddingValues = paddingValues),
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
    uiState: EmployeeDetailsViewModel.State,
) {
    when {
        uiState.isLoading -> {
            ProgressLoaderView()
        }

        uiState.error != null -> {
            GenericErrorMessageView()
        }

        else -> {
            val model = uiState.employee
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = model?.name.orEmpty(),
                    modifier = Modifier.size(50.dp, 50.dp),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
                Text(
                    text = model?.name.orEmpty(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "${model?.age} years",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 10.dp)
                )
            }

        }
    }
}

