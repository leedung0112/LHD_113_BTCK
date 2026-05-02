package com.example.btck_lhd_113.ui

import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.*
import com.example.btck_lhd_113.adapter.*
import com.example.btck_lhd_113.repository.*

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class DoiMatKhauActivity : AppCompatActivity() {

    // Khai báo view
    private lateinit var edtMatKhauCu: EditText
    private lateinit var edtMatKhauMoi: EditText
    private lateinit var edtNhapLaiMatKhauMoi: EditText
    private lateinit var btnLuuMatKhau: Button
    private lateinit var tvHuyBo: TextView
    private lateinit var imgBack: ImageView

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_doi_mat_khau)

        initViews()      // Kết nối view
        setupListeners()  // Cài đặt sự kiện
    }

    private fun initViews() {
        imgBack = findViewById(R.id.imgBack)
        edtMatKhauCu = findViewById(R.id.edtMatKhauCu)
        edtMatKhauMoi = findViewById(R.id.edtMatKhauMoi)
        edtNhapLaiMatKhauMoi = findViewById(R.id.edtNhapLaiMatKhauMoi)
        btnLuuMatKhau = findViewById(R.id.btnLuuMatKhau)
        tvHuyBo = findViewById(R.id.tvHuyBo)
    }

    private fun setupListeners() {
        // Nút quay lại
        imgBack.setOnClickListener { finish() }

        // Chữ "Hủy bỏ"
        tvHuyBo.setOnClickListener { finish() }

        // Nút "Lưu mật khẩu"
        btnLuuMatKhau.setOnClickListener {
            handleSavePassword()
        }
    }

    private fun handleSavePassword() {
        val matKhauCu = edtMatKhauCu.text.toString().trim()
        val matKhauMoi = edtMatKhauMoi.text.toString().trim()
        val nhapLai = edtNhapLaiMatKhauMoi.text.toString().trim()

        if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || nhapLai.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        if (matKhauMoi != nhapLai) {
            Toast.makeText(this, "Mật khẩu mới nhập lại không khớp", Toast.LENGTH_SHORT).show()
            return
        }

        if (matKhauMoi.length < 6) {
            Toast.makeText(this, "Mật khẩu mới phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show()
            return
        }

        val user = auth.currentUser
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, matKhauCu)
            
            // Xác thực lại trước khi đổi mật khẩu
            user.reauthenticate(credential).addOnSuccessListener {
                user.updatePassword(matKhauMoi).addOnSuccessListener {
                    Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Lỗi cập nhật: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
