package com.jlrf.mobile.employeepedia.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jlrf.mobile.employeepedia.R

@Composable
fun ProgressLoaderView(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.white))
) {
    Column(
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp, 40.dp)
        )
    }
}

@Composable
fun GenericErrorMessageView(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.white)),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Oops something went wrong!"
        )
    }
}