package com.darien.search_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.darien.core_ui.util.SharedResources
import com.darien.search_ui.R
import com.darien.search_ui.util.Resources

@Composable
fun NoDataView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .padding(
                    bottom = SharedResources.SharedDims.margin_md,
                    top = SharedResources.SharedDims.standard_button_size
                )
                .size(Resources.Dims.no_data_image_size),
            painter = painterResource(id = R.drawable.ic_search), 
            contentDescription = stringResource(id = R.string.search)
        )
        Text(
            modifier = Modifier.padding(horizontal = SharedResources.SharedDims.standard_button_size),
            text = stringResource(id = R.string.type_organization_name),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun NoDataViewPreview() {
    MaterialTheme {
        NoDataView()
    }
}