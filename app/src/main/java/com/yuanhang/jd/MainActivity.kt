package com.yuanhang.jd

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yuanhang.jd.view.SimpleToolBar


class MainActivity : AppCompatActivity() {

    private var wv: WebView? = null
    private var tvJdCk: TextView? = null
    private var exitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initWebView()
        initClickEvent()
    }

    private fun initToolbar() {
        val toolbar = findViewById<SimpleToolBar>(R.id.toolbar)
        toolbar.getTvCenter().text = "京东CK助手"
        val tvRight = toolbar.getTvRight()
        tvRight.setText("关于")
        tvRight.setCompoundDrawables(this.resources.getDrawable(R.mipmap.ic_about_me), null, null, null)
        tvRight.setOnClickListener {
            //Toast.makeText(this, "关于界面", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@MainActivity, AboutMeActivity::class.java))
        }
    }

    private fun initClickEvent() {
        val btObtainJdCk = findViewById<Button>(R.id.bt_obtain_jd_ck)
        btObtainJdCk.setOnClickListener {
            val url = wv!!.url
            val cookieManager = CookieManager.getInstance()
            val cookie = cookieManager.getCookie(url)
            if (cookie.contains("pt_pin", ignoreCase = true) && cookie.contains("pt_token", ignoreCase = true)) {
                tvJdCk!!.setText("获取ck成功,已将ck存入剪切板")
                Toast.makeText(this, "获取ck成功,已将ck存入剪切板", Toast.LENGTH_LONG)
                    .show()
                Log.d("京东cookie", cookie)
                //获取剪贴板管理器：
                val cm: ClipboardManager =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                val mClipData = ClipData.newPlainText("Label", cookie)
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData)
            } else {
                Toast.makeText(this, "获取ck失败,请检查是否登录成功", Toast.LENGTH_LONG).show()
                tvJdCk!!.setText("获取ck失败,请检查是否登录成功")
            }
        }
    }

    private fun initWebView() {
        wv = findViewById<WebView>(R.id.wv)
        tvJdCk = findViewById<TextView>(R.id.tv_jd_ck)
        //清除本地缓存数据
        clearLocalCacheData()
        if (wv != null) {
            wv!!.clearFormData()
            wv!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    return false
                }
            }
            wv!!.loadUrl("https://m.jd.com")
            wv!!.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY// 隐藏滚动条webView.requestFocus();
            wv!!.requestFocusFromTouch()

            wv!!.settings.setSupportZoom(true)
            //wv.addJavascriptInterface(this,"android")
            wv!!.settings.builtInZoomControls = true
            wv!!.settings.displayZoomControls = false
            wv!!.settings.javaScriptEnabled = true
            wv!!.settings.loadsImagesAutomatically = true
        }
    }

    //清除本地缓存数据
    private fun clearLocalCacheData() {
        deleteDatabase("webview.db")
        deleteDatabase("webviewCache.db")
        WebStorage.getInstance().deleteAllData()
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookie()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            false
        }else {
            true
        }
    }

    private fun exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show()
            exitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }
}