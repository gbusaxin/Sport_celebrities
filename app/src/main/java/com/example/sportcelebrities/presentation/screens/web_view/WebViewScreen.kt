package com.example.sportcelebrities.presentation.screens.web_view

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportcelebrities.utils.Constants.DEFAULT_URL_LINK
import com.example.sportcelebrities.utils.web_utils.CustomChromeClient
import com.example.sportcelebrities.utils.web_utils.CustomWebClient

@Composable
fun WebViewScreen(
    webViewViewModel: WebViewViewModel = hiltViewModel()
) {
    val urlResponse by webViewViewModel.linkResponse.collectAsState()

    val context = LocalContext.current
    val webView = rememberWebView()
    val webViewClient = rememberWebViewClient(context = context)
    val chromeClient = rememberWebChromeClient(context = context)

    webViewViewModel.setWebSettings(webView.settings)
    WebViewContent(
        url = urlResponse ?: DEFAULT_URL_LINK,
        webView = webView,
        webViewClient = webViewClient,
        chromeClient = chromeClient
    )
}

@Composable
fun WebViewContent(
    url: String,
    webView: WebView,
    webViewClient: WebViewClient,
    chromeClient: WebChromeClient
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(factory = { webView }) {
            webView.webViewClient = webViewClient
            webView.webChromeClient = chromeClient
            webView.loadUrl(url)
        }
    }
}

@Composable
fun rememberWebView(): WebView {
    val currentContext = LocalContext.current
    val webView = remember {
        WebView(currentContext)
    }
    webView.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    return webView
}

@Composable
fun rememberWebViewClient(context: Context): WebViewClient {
    val webViewClient = remember {
        CustomWebClient(context = context)
    }
    return webViewClient
}


@Composable
fun rememberWebChromeClient(context: Context): WebChromeClient {
    val activity = LocalContext.current as ComponentActivity
    val chromeClient = remember {
        CustomChromeClient(activity)
    }
    return chromeClient
}
