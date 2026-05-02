package com.example.btck_lhd_113.ui

import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.*
import com.example.btck_lhd_113.adapter.*
import com.example.btck_lhd_113.repository.*

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChitietbaihocActivity : AppCompatActivity() {

    // Khai báo các view
    private lateinit var imgBack: ImageView
    private lateinit var cardHocTuVung: LinearLayout
    private lateinit var cardLamBaiTap: LinearLayout
    private lateinit var cardDanhSachTuVung: LinearLayout
    private lateinit var btnNavTrangChu: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_chi_tiet_bai_hoc)

        initViews()      // Khởi tạo các view
        setupListeners()  // Thiết lập các sự kiện click
    }

    private fun initViews() {
        imgBack = findViewById(R.id.imgBack)
        cardHocTuVung = findViewById(R.id.cardHocTuVung)
        cardLamBaiTap = findViewById(R.id.cardLamBaiTap)
        cardDanhSachTuVung = findViewById(R.id.cardDanhSachTuVung)
        btnNavTrangChu = findViewById(R.id.btnNavTrangChu)
    }

    private fun setupListeners() {
        // Bấm nút Back ở góc trái trên cùng để quay lại
        imgBack.setOnClickListener {
            finish()
        }

        // Bấm thẻ Học từ vựng -> Chuyển sang màn hình HocTuVungActivity (Mới)
        cardHocTuVung.setOnClickListener {
            startActivity(Intent(this, HocTuVungActivity::class.java))
        }

        // Bấm thẻ Làm bài tập -> Chuyển sang QuizActivity
        cardLamBaiTap.setOnClickListener {
            Toast.makeText(this, "Chuyển sang làm bài tập", Toast.LENGTH_SHORT).show()
            // Kiểm tra xem QuizActivity có tồn tại không trước khi gọi
            try {
                startActivity(Intent(this, QuizActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Màn hình bài tập đang được phát triển", Toast.LENGTH_SHORT).show()
            }
        }

        // Bấm thẻ Danh sách từ vựng
        cardDanhSachTuVung.setOnClickListener {
            Toast.makeText(this, "Tính năng Danh sách từ vựng đang phát triển", Toast.LENGTH_SHORT).show()
        }

        // Bấm nút Trang chủ ở thanh điều hướng dưới cùng
        btnNavTrangChu.setOnClickListener {
            val intent = Intent(this, TrangchuActivity::class.java)
            // FLAG_ACTIVITY_CLEAR_TOP giúp xóa các màn hình trung gian để về Trang chủ
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
