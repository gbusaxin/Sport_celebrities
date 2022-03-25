package com.example.sportcelebrities.utils.web_utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CustomChromeClient(
    private val context: ComponentActivity,
    private val messageCallback: MessageCallback,
    private var mFilePathCallback: ValueCallback<Array<Uri>>?,
    private var mCameraPhotoPath: String?
) : WebChromeClient() {

    private val INPUT_FILE_REQUEST_CODE = 1
    private val FILECHOOSER_RESULTCODE = 1

    @Suppress("DEPRECATION")
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
    }

    //android 5.0
    @Suppress("DEPRECATION")
    override fun onShowFileChooser(
        view: WebView,
        filePath: ValueCallback<Array<Uri>>,
        fileChooser: FileChooserParams
    ): Boolean {
        mFilePathCallback?.let { it.onReceiveValue(null) }
        mFilePathCallback = filePath
        var takePicIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePicIntent?.resolveActivity(context.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
                takePicIntent.putExtra("PhotoFile", mCameraPhotoPath)
            } catch (e: IOException) {
                Log.e("CHECK_CHECK", "CANNOT TO CREATE AN IMAGE FILE", e)
            }
            if (photoFile != null) {
                mCameraPhotoPath = "file:" + photoFile.absolutePath
                takePicIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photoFile)
                )
            } else {
                takePicIntent = null
            }
        }
        val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
        contentSelectionIntent.setType("image/*")
        var intentArray: Array<Intent>? = null
        if (takePicIntent != null) {
            intentArray = arrayOf(takePicIntent)
        } else {
            intentArray = arrayOf()
        }
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
        startActivityForResult(context, chooserIntent, INPUT_FILE_REQUEST_CODE, null)
        return true
    }

}