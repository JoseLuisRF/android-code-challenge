package com.jlrf.mobile.employeepedia.presentation.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.presentation.viewmodels.EmployeesListViewModel
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.jlrf.mobile.employeepedia.domain.base.EmployeeFilterType

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
    EmployeesListView(state = state, onItemClick = {})
}

@Preview
@Composable
fun LoadingStatePreview() {
    val state = EmployeesListViewModel.State(
        employees = emptyList(),
        isLoading = true,
        error = null
    )
    EmployeesListView(state = state, onItemClick = {})
}

@Preview
@Composable
fun ErrorStatePreview() {
    val state = EmployeesListViewModel.State(
        employees = emptyList(),
        isLoading = false,
        error = Error()
    )
    EmployeesListView(state = state, onItemClick = {})
}

@Preview
@Composable
fun EmployeeItemPreview() {
    MaterialTheme {
        EmployeeItem(
            model = EmployeeModel(
                id = 1,
                name = "Jose Luis",
                salary = 150000.00,
                age = 29,
                profileImage = ""
            ),
            onClick = {}
        )
    }
}

@Composable
fun EmployeesScreen(
    viewModel: EmployeesListViewModel? = null,
    onItemClick: (Long) -> Unit
) {
    EmployeesListView(
        state = viewModel?.state?.collectAsState()?.value ?: EmployeesListViewModel.State(),
        onItemClick = onItemClick,
        viewModel = viewModel
    )
}

@Composable
fun EmployeesListView(
    state: EmployeesListViewModel.State,
    onItemClick: (Long) -> Unit,
    viewModel: EmployeesListViewModel? = null,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.white)
            )
    ) {
        val (filtersDropDown, itemsList, loaderView, errorView) = createRefs()
        FiltersDropDown(
            viewModel = viewModel,
            modifier = Modifier.constrainAs(filtersDropDown) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 8.dp)
            }
        )
        when {
            state.isLoading -> {
                ProgressLoaderView(
                    modifier = Modifier
                        .constrainAs(loaderView) {
                            top.linkTo(filtersDropDown.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                        }
                        .background(colorResource(id = R.color.white))
                )
            }
            state.error != null -> {
                GenericErrorMessageView(
                    modifier = Modifier
                        .constrainAs(errorView){
                            top.linkTo(filtersDropDown.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                        }
                        .background(colorResource(id = R.color.white)),
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .constrainAs(itemsList) {
                            top.linkTo(filtersDropDown.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
//                            bottom.linkTo(parent.bottom, margin = 8.dp)
                        }
                        .fillMaxSize()
                        .background(colorResource(id = R.color.white))
                ) {
                    items(state.employees.size) {
                        state.employees.forEach {
                            EmployeeItem(
                                model = it,
                                onClick = onItemClick
                            )
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun EmployeeItem(
    model: EmployeeModel,
    onClick: (Long) -> Unit
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
        currency = Currency.getInstance("USD")
    }

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick.invoke(model.id)
            }
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(
                        colorResource(id = R.color.white)
                    )
            ) {
                val (textNameRef, textAgeRef, textSalaryRef) = createRefs()
                Text(
                    text = model.name,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .constrainAs(textNameRef) {
                            top.linkTo(parent.top, margin = 16.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                        }
                )

                Text(
                    text = stringResource(R.string.years_postfix, model.age),
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .constrainAs(textAgeRef) {
                            top.linkTo(parent.top, margin = 16.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        }
                )

                Text(
                    text = numberFormat.format(model.salary),
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .constrainAs(textSalaryRef) {
                            top.linkTo(textAgeRef.bottom, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                        }
                )
            }
        }
    }
}

fun filterItems(
    viewModel: EmployeesListViewModel,
    filterTypeValue: String
) {
    when (filterTypeValue) {
        EmployeeFilterType.Salary.value -> {
            viewModel.loadEmployees(EmployeeFilterType.Salary)
        }
        EmployeeFilterType.Age.value -> {
            viewModel.loadEmployees(EmployeeFilterType.Age)
        }
        EmployeeFilterType.None.value -> {
            viewModel.loadEmployees(EmployeeFilterType.None)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FiltersDropDown(
    viewModel: EmployeesListViewModel? = null,
    modifier: Modifier
) {

    val contextForToast = LocalContext.current.applicationContext

    val listItems = arrayOf(
        EmployeeFilterType.None.value,
        EmployeeFilterType.Salary.value,
        EmployeeFilterType.Age.value
    )

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        TextField(
            value = selectedItem,
            onValueChange = { value ->

            },
            readOnly = true,
            label = { Text(text = "Filter by") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    viewModel?.let { vm ->
                        filterItems(viewModel = vm, filterTypeValue = selectedOption)
                    }
                    expanded = false
                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }
}