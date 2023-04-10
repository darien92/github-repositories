package com.darien.search_ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darien.search_data.domain_model.OrganizationDomainModel

@Composable
fun OrganizationsResultContainer(
    modifier: Modifier = Modifier,
    organizations: List<OrganizationDomainModel>,
    onItemClicked: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(organizations) { currOrg ->
                OrganizationResultItem(organization = currOrg.name) {
                    onItemClicked.invoke(it)
                }
            }
        }
    }
}