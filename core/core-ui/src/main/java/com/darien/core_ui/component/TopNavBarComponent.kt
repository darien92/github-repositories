package com.darien.core_ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.darien.core_ui.R
import com.darien.core_ui.util.SharedResources

@Composable
fun TopNavBarComponent(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SharedResources.SharedDims.top_nav_bar_height),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            modifier = Modifier
                .size(SharedResources.SharedDims.standard_button_size)
                .padding(start = SharedResources.SharedDims.margin_xs),
            onClick = {
                onBackPressed.invoke()
            }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.search)
            )
        }
        Text(
            modifier = modifier.padding(horizontal = SharedResources.SharedDims.margin_md),
            text = title,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun TopNavBarComponentPreview() {
    MaterialTheme {
        TopNavBarComponent(
            title = "My Org"
        ) {}
    }
}