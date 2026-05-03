package com.example.btck_lhd_113.ui

import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.*
import com.example.btck_lhd_113.adapter.*
import com.example.btck_lhd_113.repository.*

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class KetQuaActivity : AppCompatActivity() {

    private lateinit var txtDiemSo: TextView
    private lateinit var txtTongSo: TextView
    private lateinit var progressKetQua: ProgressBar
    private lateinit var btnLamLai: AppCompatButton
    private lateinit var btnVeTrangChu: AppCompatButton
    private lateinit var txtChucMung: TextView

    private val repo = FirebaseRepository()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_ket_qua)

        txtDiemSo = findViewById(R.id.txtDiemSo)
        txtTongSo = findViewById(R.id.txtTongSo)
        progressKetQua = findViewById(R.id.progressKetQua)
        btnLamLai = findViewById(R.id.btnLamLai)
        btnVeTrangChu = findViewById(R.id.btnVeTrangChu)
        txtChucMung = findViewById(R.id.txtChucMung)

        val diemSo = intent.getIntExtra("DIEM_SO", 0)
        val tongSo = intent.getIntExtra("TONG_SO", 5)
        val idBaiHoc = intent.getIntExtra("ID_BAI_HOC", 0)
        val loaiBaiTap = intent.getStringExtra("LOAI_BAI_TAP") // "QUIZ" hoặc "VOCAB"

        txtDiemSo.text = diemSo.toString()
        txtTongSo.text = "/ $tongSo"
        
        val phanTram = if (tongSo > 0) (diemSo * 100) / tongSo else 0
        progressKetQua.progress = phanTram

        if (phanTram >= 80) {
            txtChucMung.text = "Tuyệt vời!"
            auth.currentUser?.uid?.let { repo.addProgress(it, idBaiHoc) }
        } else if (phanTram >= 50) {
            txtChucMung.text = "Làm tốt lắm!"
            auth.currentUser?.uid?.let { repo.addProgress(it, idBaiHoc) }
        } else {
            txtChucMung.text = "Cố gắng lên!"
        }

        btnLamLai.setOnClickListener {
            val intent = if (loaiBaiTap == "QUIZ") {
                Intent(this, TracNghiemActivity::class.java)
            } else {
                Intent(this, HocTuVungActivity::class.java)
            }
            intent.putExtra("ID_BAI_HOC", idBaiHoc)
            startActivity(intent)
            finish()
        }

        btnVeTrangChu.setOnClickListener {
            val intent = Intent(this, ChitietbaihocActivity::class.java)
            intent.putExtra("ID_BAI_HOC", idBaiHoc)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        // Sự kiện cho thanh điều hướng bên dưới
        findViewById<LinearLayout>(R.id.navTrangChu).setOnClickListener {
            val intent = Intent(this, TrangchuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        
        findViewById<LinearLayout>(R.id.navBaiHoc).setOnClickListener {
            // Đã ở phần bài học hoặc về trang chủ
            val intent = Intent(this, TrangchuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.navTienDo).setOnClickListener {
            Toast.makeText(this, "Tính năng Tiến độ đang phát triển", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.navCaiDat).setOnClickListener {
            val intent = Intent(this, HosoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}
