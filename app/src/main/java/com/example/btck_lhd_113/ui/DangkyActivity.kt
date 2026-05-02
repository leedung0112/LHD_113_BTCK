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
            val hoTen = findViewById<EditText>(R.id.edtHoTen).text.toString()
            val email = findViewById<EditText>(R.id.edtTaiKhoan).text.toString()
            val pass = findViewById<EditText>(R.id.edtMatKhau).text.toString()

            if (hoTen.isEmpty() || email.isEmpty() || pass.isEmpty()) return@setOnClickListener

            auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener { res ->
                val user = NguoiDungModel(res.user?.uid ?: "", hoTen, email, listOf())
                repo.saveUser(user) {
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, TrangchuActivity::class.java))
                    finish()
                }
            }.addOnFailureListener { Toast.makeText(this, "Lỗi: ${it.message}", Toast.LENGTH_SHORT).show() }
        }

        findViewById<ImageView>(R.id.imgBack).setOnClickListener { finish() }
    }
}
