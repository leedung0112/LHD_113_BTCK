package com.example.btck_lhd_113.ui

import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.*
import com.example.btck_lhd_113.adapter.*
import com.example.btck_lhd_113.repository.*

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.text.InputType
import com.google.firebase.auth.FirebaseAuth

class DangnhapActivity : AppCompatActivity() {

    // 1. Khai báo các View
    private lateinit var edtTaiKhoan: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var imgHienThiMatKhau: ImageView
    private lateinit var btnDangNhap: Button
    private lateinit var tvDangKyNgay: TextView
    private lateinit var imgBack: ImageView

    private val auth = FirebaseAuth.getInstance()
    private var isPasswordVisible = false // Trạng thái ẩn/hiện mật khẩu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_dang_nhap)

        initViews()      // Bước 1: Ánh xạ View
        setupListeners()  // Bước 2: Cài đặt sự kiện
    }

    private fun initViews() {
        imgBack = findViewById(R.id.imgBack)
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan)
        edtMatKhau = findViewById(R.id.edtMatKhau)
        imgHienThiMatKhau = findViewById(R.id.imgHienThiMatKhau)
        btnDangNhap = findViewById(R.id.btnDangNhap)
        tvDangKyNgay = findViewById(R.id.tvDangKyNgay)
    }

    private fun setupListeners() {
        // Nút quay lại
        imgBack.setOnClickListener { finish() }

        // Hiện/Ẩn mật khẩu khi bấm vào icon con mắt
        imgHienThiMatKhau.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                edtMatKhau.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                imgHienThiMatKhau.setColorFilter(android.graphics.Color.parseColor("#1A73E8"))
            } else {
                edtMatKhau.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                imgHienThiMatKhau.setColorFilter(android.graphics.Color.parseColor("#9AA0A6"))
            }
            edtMatKhau.setSelection(edtMatKhau.text.length) // Đưa con trỏ về cuối dòng
        }

        // Chuyển sang màn hình Đăng ký
        tvDangKyNgay.setOnClickListener {
            startActivity(Intent(this, DangkyActivity::class.java))
        }

        // Xử lý khi bấm nút Đăng nhập
        btnDangNhap.setOnClickListener {
            val email = edtTaiKhoan.text.toString().trim()
            val matKhau = edtMatKhau.text.toString().trim()

            if (email.isEmpty() || matKhau.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, matKhau)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, TrangchuActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Lỗi: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
