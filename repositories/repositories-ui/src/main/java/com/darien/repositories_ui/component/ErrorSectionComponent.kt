package com.darien.repositories_ui.component

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
import com.darien.core_data.NetworkResponseCodes
import com.darien.core_ui.util.SharedResources
import com.darien.repositories_ui.R
import com.darien.repositories_ui.util.Resources

@Composable
fun ErrorSectionComponent(
    modifier: Modifier = Modifier,
    responseStatus: NetworkResponseCodes
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(
                    bottom = SharedResources.SharedDims.margin_md,
                    top = SharedResources.SharedDims.standard_button_size
                )
                .size(Resources.Dims.error_image_size),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = stringResource(id = R.string.error)
        )
        Text(
            modifier = Modifier.padding(horizontal = SharedResources.SharedDims.standard_button_size),
            text = getNetworkResponseMessage(responseStatus = responseStatus),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Composable
internal fun getNetworkResponseMessage(responseStatus: NetworkResponseCodes): String {
    return when (responseStatus) {
        NetworkResponseCodes.VALIDATION_FAILED -> {
            stringResource(id = R.string.validation_failed_message)
        }
        NetworkResponseCodes.SERVICE_UNAVAILABLE -> {
            stringResource(id = R.string.service_unavailable_message)
        }
        NetworkResponseCodes.CONNECTION_ERROR -> {
            stringResource(id = R.string.connection_error_message)
        }
        else -> {
            stringResource(id = R.string.unknown_error_message)
        }
    }
}