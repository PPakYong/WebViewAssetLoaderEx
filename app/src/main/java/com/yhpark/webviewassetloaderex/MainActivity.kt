package com.yhpark.webviewassetloaderex

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewAssetLoader.InternalStoragePathHandler
import androidx.webkit.WebViewAssetLoader.ResourcesPathHandler
import androidx.webkit.WebViewClientCompat
import com.yhpark.webviewassetloaderex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webViewAssetLoader: WebViewAssetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", AssetsPathHandler(this))
            .addPathHandler("/", InternalStoragePathHandler(this, filesDir))
            .build()

        binding.webView.webViewClient = ToyTestWebViewClient(webViewAssetLoader)

        val webviewSettings = binding.webView.settings
        webviewSettings.allowFileAccessFromFileURLs = false
        webviewSettings.allowUniversalAccessFromFileURLs = false

//        var url: String = "https://appassets.androidplatform.net/assets/sample.html"
        var url = "http://www.limsajang.synology.me"
        binding.webView.loadUrl(url)
    }

    class ToyTestWebViewClient(webViewAssetLoader: WebViewAssetLoader) : WebViewClientCompat() {
        var webViewAssetLoader : WebViewAssetLoader
        init {
            this.webViewAssetLoader = webViewAssetLoader
        }
        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
            return webViewAssetLoader.shouldInterceptRequest(request!!.url)
        }
    }
}