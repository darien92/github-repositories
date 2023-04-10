package com.darien.repositories_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.darien.core_ui.util.SharedResources
import com.darien.repositories_data.model.domain.RepositoryDomainModel
import com.darien.repositories_ui.R

@Composable
fun RepositoryItem(
    modifier: Modifier = Modifier,
    model: RepositoryDomainModel,
    onRepositoryPressed: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(SharedResources.SharedDims.margin_xs)
            .clickable {
                onRepositoryPressed(model.htmlUrl)
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SharedResources.SharedDims.margin_xs),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(SharedResources.SharedDims.standard_button_size),
                    painter = rememberImagePainter(model.avatar),
                    contentDescription = stringResource(id = R.string.avatar)
                )
                Text(
                    text = model.name,
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                StarSection(
                    modifier = Modifier.padding(
                        top = SharedResources.SharedDims.margin_xxxs,
                        start = SharedResources.SharedDims.margin_xs
                    ),
                    rating = model.parseStars()
                )
            }
            Text(
                modifier = modifier.padding(horizontal = SharedResources.SharedDims.margin_xs),
                text = model.description,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.padding(SharedResources.SharedDims.margin_xs),
            ) {
                Text(
                    text = "${stringResource(id = R.string.language)} ${model.language}",
                    style = MaterialTheme.typography.overline
                )
                Spacer(modifier = Modifier.width(SharedResources.SharedDims.margin_md))
                Text(
                    text = "${stringResource(id = R.string.updated_on)} ${model.parseDate()}",
                    style = MaterialTheme.typography.overline
                )
            }
        }
    }
}

@Preview
@Composable
fun RepositoryItemPreview() {
    val domainModel = RepositoryDomainModel(
        id = 123,
        name = "Repo name",
        avatar = "",
        htmlUrl = "",
        description = "This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description. This is a very long repository description.",
        language = "Kotlin",
        starsCount = 120000,
        lastUpdated = "2023-04-10T03:21:46Z"
    )
    MaterialTheme {
        RepositoryItem(model = domainModel) {

        }
    }
}