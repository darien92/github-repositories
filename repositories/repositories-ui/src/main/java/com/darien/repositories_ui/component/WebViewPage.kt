package com.darien.repositories_ui.component

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewPage(
    modifier: Modifier = Modifier,
    url: String
) {
    Surface(modifier = modifier) {
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
            update = {
                it.loadUrl(url)
            }
        )
    }
}