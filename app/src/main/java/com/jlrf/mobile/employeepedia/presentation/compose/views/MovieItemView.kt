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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.util.extensions.getYearFromDateString


@Preview
@Composable
fun EmployeeItemPreview() {
    MovieItemView(
        model = MovieModel(
            genreIds = emptyList(),
            id = 1,
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Overview",
            popularity = 1.0,
            posterPath = "posterPath",
            releaseDate = "2024-07-02",
            title = "Title",
            backdropPath = "backdropPath",
        ),
        onClick = {}
    )
}

@Composable
fun MovieItemView(
    model: MovieModel,
    onClick: (MovieModel) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp, top = 8.dp)
            .clickable {
                onClick.invoke(model)
            }
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .width(200.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model.posterPath),
                contentDescription = model.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp
                    )
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = model.releaseDate.getYearFromDateString().orEmpty(),
                    color = colorResource(id = R.color.black),
                )
            }
        }
    }
}