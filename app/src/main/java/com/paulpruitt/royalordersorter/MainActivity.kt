package com.paulpruitt.royalordersorter

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URLDecoder

class MainActivity : AppCompatActivity() {
    
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        
        setupWebView()
        loadApp()
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            cacheMode = WebSettings.LOAD_DEFAULT
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            useWideViewPort = true
            loadWithOverviewMode = true
            databaseEnabled = true
        }
        
        // Enable debugging in development
        WebView.setWebContentsDebuggingEnabled(true)
        
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
            
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (request?.isForMainFrame == true) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error loading page: ${error?.description}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                }
            }
            
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.let {
                    android.util.Log.d("WebView", "${it.message()} -- From line ${it.lineNumber()} of ${it.sourceId()}")
                }
                return true
            }
        }
        
        // Handle file downloads (for TXT, DOCX, PDF exports)
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            handleDownload(url, contentDisposition, mimetype)
        }
    }
    
    private fun handleDownload(url: String, contentDisposition: String, mimetype: String) {
        try {
            if (url.startsWith("blob:")) {
                // Handle blob URLs - inject JavaScript to convert blob to data URL
                webView.evaluateJavascript("""
                    (function() {
                        var link = document.createElement('a');
                        link.href = '$url';
                        link.click();
                    })();
                """.trimIndent(), null)
                Toast.makeText(this, "File download initiated", Toast.LENGTH_SHORT).show()
            } else if (url.startsWith("data:")) {
                // Handle data URLs
                val filename = URLDecoder.decode(
                    URLContentDisposition.parse(contentDisposition)?.filename ?: "download",
                    "UTF-8"
                )
                Toast.makeText(this, "Downloading: $filename", Toast.LENGTH_SHORT).show()
            } else {
                // Standard download
                val request = DownloadManager.Request(Uri.parse(url))
                request.setMimeType(mimetype)
                request.addRequestHeader("User-Agent", webView.settings.userAgentString)
                request.setDescription("Downloading file...")
                request.setTitle(URLContentDisposition.parse(contentDisposition)?.filename ?: "download")
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    URLContentDisposition.parse(contentDisposition)?.filename ?: "download"
                )
                
                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Download error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun loadApp() {
        progressBar.visibility = View.VISIBLE
        webView.loadUrl("file:///android_asset/index.html")
    }
    
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
    
    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}

// Helper class to parse Content-Disposition header
object URLContentDisposition {
    data class ParseResult(val filename: String?)
    
    fun parse(contentDisposition: String): ParseResult? {
        return try {
            val filenameRegex = """filename[^;=\n]*=((['"]).*?\2|[^;\n]*)""".toRegex()
            val matchResult = filenameRegex.find(contentDisposition)
            val filename = matchResult?.groupValues?.get(1)?.trim('"', '\'')
            ParseResult(filename)
        } catch (e: Exception) {
            null
        }
    }
}
