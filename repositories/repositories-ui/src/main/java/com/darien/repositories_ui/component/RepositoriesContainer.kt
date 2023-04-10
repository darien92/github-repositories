package com.darien.repositories_ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.darien.repositories_data.model.domain.RepositoryDomainModel

@Composable
fun RepositoriesContainer(
    repositories: List<RepositoryDomainModel>,
    onRepositoryClicked: (String) -> Unit
) {
    LazyColumn {
        items(repositories) { currRepository ->
            RepositoryItem(
                model = currRepository,
            ) {
                onRepositoryClicked(it)
            }
        }
    }
}