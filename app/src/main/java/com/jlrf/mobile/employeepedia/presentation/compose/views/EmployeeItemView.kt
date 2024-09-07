package com.jlrf.mobile.employeepedia.presentation.compose.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


@Preview
@Composable
fun EmployeeItemPreview() {
    EmployeeItemView(
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

@Composable
fun EmployeeItemView(
    model: EmployeeModel,
    onClick: (Long) -> Unit
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
        currency = Currency.getInstance("USD")
    }

    Card(
        elevation = CardDefaults.cardElevation(10.dp),
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