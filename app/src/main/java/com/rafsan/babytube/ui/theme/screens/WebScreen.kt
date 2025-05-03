package com.rafsan.babytube.ui.theme.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebScreen(url: String, onBackPressed: () -> Unit) {
    val webView = rememberWebViewWithHistory()
    var canGoBack by remember { mutableStateOf(false) }

    BackHandler(enabled = canGoBack) {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            onBackPressed()
        }
    }

    WebViewWithHistory(
        url = url,
        webView = webView,
        onHistoryUpdated = { canGoBack = it },
        onPageStarted = { currentUrl ->
            println("Page started loading: $currentUrl")
        },
        onPageFinished = { currentUrl ->
            println("Page finished loading: $currentUrl")
        },
        onError = {
            println("Error loading page")
        }
    )
}

@Composable
fun rememberWebViewWithHistory(): WebView {
    val context = LocalContext.current
    return remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.setSupportMultipleWindows(false)
        }
    }
}

@Composable
fun WebViewWithHistory(
    url: String,
    webView: WebView,
    modifier: Modifier = Modifier,
    onHistoryUpdated: (Boolean) -> Unit = {},
    onPageStarted: (String) -> Unit = {},
    onPageFinished: (String) -> Unit = {},
    onError: () -> Unit = {}
) {
    AndroidView(
        factory = { webView },
        modifier = modifier,
        update = { view ->
            if (view.url != url) {
                view.loadUrl(url)
            }

            view.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    url?.let { onPageStarted(it) }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    url?.let { onPageFinished(it) }
                    onHistoryUpdated(view?.canGoBack() ?: false)
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    onError()
                }

                override fun doUpdateVisitedHistory(
                    view: WebView?,
                    url: String?,
                    isReload: Boolean
                ) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    onHistoryUpdated(view?.canGoBack() ?: false)
                }
            }
        }
    )
}