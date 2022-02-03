package com.yuanhang.jd

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yuanhang.jd.view.SimpleToolBar

class AboutMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)
        initToolbar()
        val tvUrl = findViewById<TextView>(R.id.tv_url)
        tvUrl.setOnClickListener {
            val uri: Uri = Uri.parse("https://github.com/136245813/JdCookie")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun initToolbar() {
        val toolbar = findViewById<SimpleToolBar>(R.id.toolbar)
        toolbar.getTvCenter().text = "关于应用"
    }
}