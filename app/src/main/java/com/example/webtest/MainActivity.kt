package com.example.webtest

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.webkit.*
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var z: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val web: WebView = findViewById(R.id.web)
        val edittext: EditText = findViewById(R.id.copy_edittext)
        val btn: ImageButton = findViewById(R.id.copy_btn)

        btn.setOnClickListener {
            z = edittext.text.toString()
            web.evaluateJavascript("( function(){ window.z = '${edittext.text}';})();", null)
        }

        web.settings.javaScriptEnabled = true
        web.webChromeClient = WebChromeClient()
        web.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                web.evaluateJavascript(
                    "(function() { window.z = '${z}' \n" +
                            "var inputs = document.getElementsByTagName('input')\n" +
                            "for (var i = 0; i < inputs.length; i++) {\n" +
                            "inputs[i].addEventListener('mousedown', event => {  event.target.value = window.z;})\n" +
                            "}})();",
                    null
                )
            }
        }

        web.loadUrl("https://donyoung.house.gov/contact/")
    }
}