package com.jlrf.mobile.employeepedia.presentation.compose.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jlrf.mobile.employeepedia.R
import com.jlrf.mobile.employeepedia.domain.models.MovieReviewModel

/***************************************************************************************************
 *  Composable Previews
 **************************************************************************************************/

@Preview
@Composable
fun MovieReviewItemViewPreview() {
    MovieReviewItemView(
        model = MovieReviewModel(
            id = "id",
            movieId = 1,
            author = "author",
            content = "content",
            avatarPath = "",
            rating = 1.0,
            createdAt = "createdAt",
            updatedAt = "updatedAt"
        )
    )
}

/***************************************************************************************************
 *  Composable Views
 **************************************************************************************************/
@Composable
fun MovieReviewItemView(
    model: MovieReviewModel
) {
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row {
                if (model.avatarPath.isNotBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(model.avatarPath),
                        contentDescription = "",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = "",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                }
                Text(
                    text = model.author,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth()
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                text = model.content,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}