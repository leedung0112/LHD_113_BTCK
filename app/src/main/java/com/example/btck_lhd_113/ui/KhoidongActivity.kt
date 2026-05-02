package com.example.btck_lhd_113.ui

import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.*
import com.example.btck_lhd_113.adapter.*
import com.example.btck_lhd_113.repository.*

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class KhoidongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_khoi_dong) // Sửa lại tên layout đúng

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var progress = 0

        // Sử dụng Handler để tạo hiệu ứng thanh tiến trình chạy (loading)
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                progress += 2
                progressBar.progress = progress
                
                if (progress < 100) {
                    // Tiếp tục chạy sau 30ms
                    handler.postDelayed(this, 30)
                } else {
                    // Khi đủ 100% thì chuyển sang màn hình Đăng nhập
                    startActivity(Intent(this@KhoidongActivity, DangnhapActivity::class.java))
                    finish()
                }
            }
        }
        handler.postDelayed(runnable, 50)
    }
}

