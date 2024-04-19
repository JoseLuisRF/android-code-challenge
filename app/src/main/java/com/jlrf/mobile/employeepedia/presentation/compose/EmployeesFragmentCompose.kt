package com.jlrf.mobile.employeepedia.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
        onItemClick = onItemClick
    )
}

@Composable
fun EmployeesListView(
    state: EmployeesListViewModel.State,
    onItemClick: (Long) -> Unit
) {
    when {
        state.isLoading -> {
            ProgressLoaderView()
        }
        state.error != null -> {
            GenericErrorMessageView()
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
            ) {
                items(state.employees) {
                    EmployeeItem(
                        model = it,
                        onClick = onItemClick
                    )
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