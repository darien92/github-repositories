package com.darien.repositories_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.darien.core_ui.util.SharedResources
import com.darien.repositories_ui.R
import com.darien.repositories_ui.util.Resources

@Composable
fun StarSection(
    modifier: Modifier = Modifier,
    rating: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(Resources.Dims.star_image_size),
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = stringResource(id = R.string.star)
        )
        Spacer(modifier = modifier.height(SharedResources.SharedDims.margin_xxxs))
        Text(text = rating, style = MaterialTheme.typography.overline)
    }
}