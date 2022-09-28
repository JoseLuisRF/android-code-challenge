package com.jlrf.mobile.employeepedia.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.presentation.viewmodels.EmployeeDetailsViewModel

@Composable
fun EmployeeDetailsScreen(
    viewModel: EmployeeDetailsViewModel? = null
) {
    EmployeeDetailsView(
        state = viewModel?.state?.collectAsState()?.value ?: EmployeeDetailsViewModel.State()
    )
}

@Composable
fun EmployeeDetailsView(
    state: EmployeeDetailsViewModel.State
) {
    when {
        state.isLoading -> {
            ProgressLoaderView()
        }
        state.error != null -> {
            GenericErrorMessageView()
        }
        else -> {
            val model = state.employee
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
                    color =  colorResource(id = R.color.black),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "${model?.age} years",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color =  colorResource(id = R.color.black),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 10.dp)
                )
            }

        }
    }
}
