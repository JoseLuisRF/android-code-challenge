package com.jlrf.mobile.employeepedia.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jlrf.mobile.employeepedia.presentation.compose.CustomTheme
import com.jlrf.mobile.employeepedia.presentation.compose.navigation.CustomAppNavigation
import com.jlrf.mobile.employeepedia.presentation.compose.screens.EmployeeDetailsScreen
import com.jlrf.mobile.employeepedia.presentation.compose.screens.HomeScreen
import com.jlrf.mobile.employeepedia.presentation.viewmodels.EmployeesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<EmployeesListViewModel>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadEmployees()
            }
        }
        setupUi()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    private fun setupUi() {
        setContent {
            val navController = rememberNavController()
            val windowSizeClass = calculateWindowSizeClass(this)
            val uiState by viewModel.state.collectAsState()
            CustomTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = CustomAppNavigation.HomeScreen.route,
                        route = CustomAppNavigation.NAV_GRAPH_NAME
                    ) {
                        composable(CustomAppNavigation.HomeScreen.route) {
                            HomeScreen(
                                windowSize = windowSizeClass,
                                uiState = uiState,
                                onItemClick = {
                                    viewModel.selectEmployee(it)
                                    navController.navigate(CustomAppNavigation.EmployeeDetailsScreen.route)
                                }
                            )
                        }

                        composable(CustomAppNavigation.EmployeeDetailsScreen.route) {
                            EmployeeDetailsScreen(
                                windowSize = windowSizeClass,
                                employeeId = uiState.selectedEmployee!!.id,
                                onBackPressed = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
