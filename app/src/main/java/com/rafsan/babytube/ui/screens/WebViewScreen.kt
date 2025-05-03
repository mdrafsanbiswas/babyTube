package com.rafsan.babytube.ui.screens

import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.focusable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(url: String, onBackPressed: () -> Unit) {
    val webView = rememberWebViewWithHistory()
    var canGoBack by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    // Handle back button press
    BackHandler(enabled = canGoBack) {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            onBackPressed()
        }
    }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }

    WebViewWithHistory(
        url = url,
        webView = webView,
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusable()
            .onKeyEvent { keyEvent ->
                when (keyEvent.nativeKeyEvent.keyCode) {
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        webView.scrollBy(0, -100)
                        true
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        webView.scrollBy(0, 100)
                        true
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        webView.scrollBy(-100, 0)
                        true
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        webView.scrollBy(100, 0)
                        true
                    }
                    else -> false
                }
            },
        onHistoryUpdated = { canGoBack = it },
        onPageStarted = { currentUrl ->
            println("Page started loading: $currentUrl")
        },
        onPageFinished = { currentUrl ->
            println("Page finished loading: $currentUrl")
            // Re-request focus after page loads to ensure continued scrolling
            focusRequester.requestFocus()
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
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportMultipleWindows(false)
                useWideViewPort = true
                loadWithOverviewMode = true
            }

            // Enhanced focus and scrolling settings
            isFocusable = true
            isFocusableInTouchMode = true
            isScrollContainer = true
            overScrollMode = View.OVER_SCROLL_ALWAYS
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