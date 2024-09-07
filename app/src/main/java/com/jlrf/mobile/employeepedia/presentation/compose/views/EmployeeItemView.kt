package com.jlrf.mobile.employeepedia.presentation.compose.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.MovieModel


@Preview
@Composable
fun EmployeeItemPreview() {
    EmployeeItemView(
        model = MovieModel(
            genreIds = emptyList(),
            id = 1,
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Overview",
            popularity = 1.0,
            posterPath = "posterPath",
            releaseDate = "releaseDate",
            title = "Title",
        ),
        onClick = {}
    )
}

@Composable
fun EmployeeItemView(
    model: MovieModel,
    onClick: (MovieModel) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick.invoke(model)
            }
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .width(200.dp)
        ) {
            if (model.posterPath.isNotBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(model.posterPath),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .padding(10.dp)

                )
            } else {
                // Display placeholder
            }

            Text(
                text = model.title,
                color = colorResource(id = R.color.black),
            )

            Text(
                text = model.releaseDate,
                color = colorResource(id = R.color.black),
            )
        }
    }
}