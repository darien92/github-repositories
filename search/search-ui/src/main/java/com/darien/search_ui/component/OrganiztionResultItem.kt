package com.darien.search_ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darien.search_ui.util.Resources

@Composable
fun OrganizationResultItem(
    modifier: Modifier = Modifier,
    organization: String,
    onOrganizationSelected: (String) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(Resources.Dims.organization_result_item_height)
        .clickable {
            onOrganizationSelected.invoke(organization)
        }) {
        Column(modifier.fillMaxWidth()) {
            Text(text = organization)
            Divider()
        }
    }
}