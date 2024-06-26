package com.jlrf.mobile.employeepedia.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jlrf.mobile.employeepedia.presentation.compose.EmployeesScreen
import com.jlrf.mobile.employeepedia.presentation.viewmodels.EmployeesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeesFragment : Fragment() {

    private val viewModel: EmployeesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    EmployeesScreen(
                        viewModel = viewModel,
                        onItemClick = { navigateToEmployeeDetails(it) }
                    )
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadEmployees()
            }
        }
    }

    private fun navigateToEmployeeDetails(employeeId: Long) {
        val directions = EmployeesFragmentDirections.actionEmployeesFragmentToEmployeeDetailsFragment(employeeId)
        findNavController().navigate(directions)
    }
}
