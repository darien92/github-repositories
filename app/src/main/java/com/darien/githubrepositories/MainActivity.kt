package com.darien.githubrepositories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.darien.githubrepositories.ui.theme.GithubRepositoriesTheme
import com.darien.navigation.navgraph.GithubRepositoriesNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubRepositoriesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GithubRepositoriesNavigation()
                }
            }
        }
    }
}