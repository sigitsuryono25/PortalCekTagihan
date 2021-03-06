package com.surelabsid.lauwba.retribusipasar

import am.appwise.components.ni.NoInternetDialog
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var cm: ConnectivityManager? = null
    var webView: WebView? = null
    var swipe: SwipeRefreshLayout? = null
    var link = "http://retribusi.server4111.com/index.php/cek-tagihan"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        swipe = findViewById(R.id.swipe)
        webView?.settings?.javaScriptEnabled = true
        webView?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                webView?.visibility = View.VISIBLE
            }
        }
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                swipe?.isRefreshing = false
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                link = url
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                webView?.loadUrl("about:blank")
                super.onReceivedError(view, errorCode, description, failingUrl)
                val mBuilder = NoInternetDialog.Builder(this@MainActivity)
                mBuilder.setBgGradientCenter(resources.getColor(R.color.green_200))
                mBuilder.setBgGradientEnd(resources.getColor(R.color.green_200))
                mBuilder.setBgGradientStart(resources.getColor(R.color.green_200))
                mBuilder.setButtonColor(resources.getColor(R.color.pink_200))
                mBuilder.setButtonTextColor(resources.getColor(R.color.black))
                val mNoIntenetDialog = mBuilder.build()

                mNoIntenetDialog.showDialog()
            }
        }


        if (checkPermission()) checkInternet()
        swipe?.setOnRefreshListener {
            webView?.loadUrl(link)
            Log.d("LINK", link)
            swipe?.isRefreshing = true
        }
    }

    private fun checkPermission(): Boolean {
        val permission = Manifest.permission.ACCESS_WIFI_STATE
        val res = applicationContext.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    private fun checkInternet() {
        cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null && cm?.activeNetworkInfo != null && cm?.activeNetworkInfo?.isConnected == true) {
            webView?.loadUrl(link)
            webView?.visibility = View.VISIBLE
        } else {
            webView?.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        finish()
    }
}