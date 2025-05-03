package com.rafsan.babytube.ui.theme.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.compose.BackHandler
import androidx.compose.ui.platform.LocalContext

@Composable
fun WebViewWithBackHandler(
    url: String,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onPageStarted: (String) -> Unit = {},
    onPageFinished: (String) -> Unit = {},
) {

    val context = LocalContext.current

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    url?.let { onPageStarted(it) }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    url?.let { onPageFinished(it) }
                }
            }
        }
    }

    BackHandler(enabled = webView.canGoBack()) {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            onBackPressed()
        }
    }

    AndroidView(
        factory = { webView },
        modifier = modifier,
        update = { view ->
            if (view.url != url) {
                view.loadUrl(url)
            }
        }
    )
}