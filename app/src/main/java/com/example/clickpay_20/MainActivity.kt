package com.example.clickpay_20

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val req_permission = 4643
    var mRequest : PermissionRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://possum.com.ar/pagar")

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.userAgentString = ("Possum")
        webView.webChromeClient = object : WebChromeClient(){
            override fun onPermissionRequest(request: PermissionRequest?) {
                for (r in request!!.resources){
                    if(r== PermissionRequest.RESOURCE_VIDEO_CAPTURE){
                        mRequest = request
                        requestPermissions(arrayOf(Manifest.permission.CAMERA), req_permission)
                    }
                }
            }
        }


    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        }else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==req_permission){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                mRequest!!.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
            }
        }
    }
}