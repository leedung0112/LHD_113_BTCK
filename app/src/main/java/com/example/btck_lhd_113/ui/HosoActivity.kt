package com.example.btck_lhd_113.ui

import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.*
import com.example.btck_lhd_113.adapter.*
import com.example.btck_lhd_113.repository.*

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HosoActivity : AppCompatActivity() {

    // Khai báo các view
    private lateinit var itemThongTinCaNhan: LinearLayout
    private lateinit var itemDoiMatKhau: LinearLayout
    private lateinit var itemThongBao: LinearLayout
    private lateinit var itemNgonNgu: LinearLayout
    private lateinit var btnDangXuat: LinearLayout
    private lateinit var tabTrangChu: LinearLayout
    private lateinit var tabTienDo: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_ho_so)

        initViews()      // Kết nối view
        setupListeners()  // Cài đặt sự kiện
    }

    private fun initViews() {
        itemThongTinCaNhan = findViewById(R.id.hoSo_itemThongTinCaNhan)
        itemDoiMatKhau     = findViewById(R.id.hoSo_itemDoiMatKhau)
        itemThongBao       = findViewById(R.id.hoSo_itemThongBao)
        itemNgonNgu        = findViewById(R.id.hoSo_itemNgonNgu)
        btnDangXuat        = findViewById(R.id.hoSo_btnDangXuat)
        tabTrangChu        = findViewById(R.id.hoSo_tabTrangChu)
        tabTienDo          = findViewById(R.id.hoSo_tabTienDo)
    }

    private fun setupListeners() {
        // Thông tin cá nhân
        itemThongTinCaNhan.setOnClickListener {
            Toast.makeText(this, "Tính năng Thông tin cá nhân đang phát triển", Toast.LENGTH_SHORT).show()
        }

        // Đổi mật khẩu
        itemDoiMatKhau.setOnClickListener {
            startActivity(Intent(this, DoiMatKhauActivity::class.java))
        }

        // Thông báo
        itemThongBao.setOnClickListener {
            Toast.makeText(this, "Tính năng Thông báo đang phát triển", Toast.LENGTH_SHORT).show()
        }

        // Chọn ngôn ngữ
        itemNgonNgu.setOnClickListener {
            hienThiDialogChonNgonNgu()
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
            Toast.makeText(this, "Màn hình Tiến độ đang phát triển", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hienThiDialogChonNgonNgu() {
        val dsNgonNgu = arrayOf("Tiếng Việt", "English", "日本語", "中文")
        AlertDialog.Builder(this)
            .setTitle("Chọn ngôn ngữ hiển thị")
            .setItems(dsNgonNgu) { _, viTri ->
                Toast.makeText(this, "Đã chọn: ${dsNgonNgu[viTri]}", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun hienThiDialogDangXuat() {
        AlertDialog.Builder(this)
            .setTitle("Đăng xuất")
            .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
            .setPositiveButton("Đăng xuất") { _, _ ->
                val intent = Intent(this, DangnhapActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}
