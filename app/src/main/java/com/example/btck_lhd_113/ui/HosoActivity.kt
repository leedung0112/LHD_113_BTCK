package com.example.btck_lhd_113.ui

import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.*
import com.example.btck_lhd_113.adapter.*
import com.example.btck_lhd_113.repository.*

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.btck_lhd_113.repository.FirebaseRepository

class HosoActivity : AppCompatActivity() {

    // Khai báo các view
    private lateinit var itemThongTinCaNhan: LinearLayout
    private lateinit var itemDoiMatKhau: LinearLayout
    private lateinit var itemThongBao: LinearLayout
    private lateinit var itemNgonNgu: LinearLayout
    private lateinit var btnDangXuat: LinearLayout
    private lateinit var tabTrangChu: LinearLayout
    private lateinit var tabTienDo: LinearLayout
    private lateinit var tvHoTen: TextView

    private val auth = FirebaseAuth.getInstance()
    private val repo = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_ho_so)

        initViews()      // Kết nối view
        setupListeners()  // Cài đặt sự kiện
        loadUserData()    // Tải thông tin người dùng
    }

    private fun initViews() {
        itemThongTinCaNhan = findViewById(R.id.hoSo_itemThongTinCaNhan)
        itemDoiMatKhau     = findViewById(R.id.hoSo_itemDoiMatKhau)
        itemThongBao       = findViewById(R.id.hoSo_itemThongBao)
        itemNgonNgu        = findViewById(R.id.hoSo_itemNgonNgu)
        btnDangXuat        = findViewById(R.id.hoSo_btnDangXuat)
        tabTrangChu        = findViewById(R.id.hoSo_tabTrangChu)
        tabTienDo          = findViewById(R.id.hoSo_tabTienDo)
        tvHoTen            = findViewById(R.id.hoSo_tvHoTen)
    }

    private fun loadUserData() {
        val uid = auth.currentUser?.uid ?: return
        repo.getUser(uid) { user ->
            if (user != null) {
                tvHoTen.text = user.hoten
            }
        }
    }

    private fun setupListeners() {
        // Thông tin cá nhân (Cho phép đổi tên)
        itemThongTinCaNhan.setOnClickListener {
            hienThiDialogDoiTen()
        }

        // Đổi mật khẩu
        itemDoiMatKhau.setOnClickListener {
            startActivity(Intent(this, DoiMatKhauActivity::class.java))
        }

        // Thông báo
        itemThongBao.setOnClickListener {
            Toast.makeText(this, "Tính năng Thông báo đang phát triển", Toast.LENGTH_SHORT).show()
        }

        itemNgonNgu.setOnClickListener {
            Toast.makeText(this,"Tính năng Ngôn ngữ đang phát triển", Toast.LENGTH_SHORT).show()
        }


        // Đăng xuất
        btnDangXuat.setOnClickListener {
            hienThiDialogDangXuat()
        }

        // Bottom Navigation: Trang chủ
        tabTrangChu.setOnClickListener {
            val intent = Intent(this, TrangchuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        // Bottom Navigation: Tiến độ
        tabTienDo.setOnClickListener {
            Toast.makeText(this, "Tính năng Tiến độ đang phát triển", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hienThiDialogDoiTen() {
        val uid = auth.currentUser?.uid ?: return
        val edt = android.widget.EditText(this)
        edt.setText(tvHoTen.text)
        
        AlertDialog.Builder(this)
            .setTitle("Đổi họ tên")
            .setView(edt)
            .setPositiveButton("Lưu") { _, _ ->
                val tenMoi = edt.text.toString().trim()
                if (tenMoi.isNotEmpty()) {
                    repo.getUser(uid) { user ->
                        if (user != null) {
                            val newUser = user.copy(hoten = tenMoi)
                            repo.saveUser(newUser) {
                                tvHoTen.text = tenMoi
                                Toast.makeText(this, "Đã cập nhật tên!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun hienThiDialogDangXuat() {
        AlertDialog.Builder(this)
            .setTitle("Đăng xuất")
            .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
            .setPositiveButton("Đăng xuất") { _, _ ->
                auth.signOut()
                val intent = Intent(this, DangnhapActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}
