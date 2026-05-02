package com.example.btck_lhd_113.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.NguoiDungModel
import com.example.btck_lhd_113.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth

class DangkyActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val repo = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_dang_ky)

        findViewById<Button>(R.id.btnTaoTaiKhoan).setOnClickListener {
            val hoTen = findViewById<EditText>(R.id.edtHoTen).text.toString().trim()
            val email = findViewById<EditText>(R.id.edtTaiKhoan).text.toString().trim()
            val pass = findViewById<EditText>(R.id.edtMatKhau).text.toString().trim()
            val rePass = findViewById<EditText>(R.id.edtNhapLaiMatKhau).text.toString().trim()

            if (hoTen.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != rePass) {
                Toast.makeText(this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener { res ->
                val user = NguoiDungModel(res.user?.uid ?: "", hoTen, email, listOf())
                repo.saveUser(user) {
                    Toast.makeText(this, "Đăng ký thành công! Mời bạn đăng nhập", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, DangnhapActivity::class.java))
                    finish()
                }
            }.addOnFailureListener { 
                Toast.makeText(this, "Lỗi: ${it.message}", Toast.LENGTH_LONG).show() 
            }
        }

        findViewById<ImageView>(R.id.imgBack).setOnClickListener { finish() }

        findViewById<TextView>(R.id.tvDangNhapNgay).setOnClickListener {
            startActivity(Intent(this, DangnhapActivity::class.java))
            finish()
        }
    }
}
