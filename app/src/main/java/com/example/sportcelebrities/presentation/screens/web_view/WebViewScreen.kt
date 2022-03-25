package com.example.sportcelebrities.presentation.screens.web_view

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportcelebrities.MainActivity
import com.example.sportcelebrities.utils.web_utils.CustomWebClient
import com.example.sportcelebrities.utils.Constants.DEFAULT_URL_LINK

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
     // I am the dangerous
    val chromeClient = remember {

    }
    return chromeClient
}
