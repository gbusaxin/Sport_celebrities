package com.example.sportcelebrities.utils.web_utils

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts


class CustomChromeClient(val context: ComponentActivity) : WebChromeClient() {

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
        val intent = fileChooserParams?.createIntent()
        intent?.addCategory(Intent.ACTION_OPEN_DOCUMENT)
        val i = fileChooserParams?.createIntent()?.addCategory(Intent.ACTION_OPEN_DOCUMENT)
        val activityResult = context.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
        }
        Log.d("CHECK_INTENT","$intent + before")
        activityResult.launch(intent)
        Log.d("CHECK_INTENT","$intent + after")
        return true
    }


}