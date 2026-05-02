package com.example.btck_lhd_113.ui

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.adapter.BaiHocAdapter
import com.example.btck_lhd_113.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth

class TienDoActivity : AppCompatActivity() {

    private val repo = FirebaseRepository()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_tien_do)

        rv = findViewById(R.id.rvDanhSachBaiHoc)
        rv.layoutManager = LinearLayoutManager(this)

        val uid = auth.currentUser?.uid ?: ""
        repo.getUser(uid) { user ->
            val idDaHoc = user?.tien_do ?: listOf()
            repo.getBaiHoc { tatCa ->
                capNhatUI(idDaHoc.size, tatCa.size)
                rv.adapter = BaiHocAdapter(tatCa) { }
            }
        }

        findViewById<TextView>(R.id.btnTrangChu).setOnClickListener { finish() }
    }

    private fun capNhatUI(daHoc: Int, tong: Int) {
        val phanTram = if (tong > 0) (daHoc * 100) / tong else 0
        findViewById<TextView>(R.id.txtPhanTram).text = "$phanTram%"
        findViewById<ProgressBar>(R.id.thanhPhanTram).progress = phanTram
        findViewById<TextView>(R.id.txtTieuDeTienDo).text = "$daHoc/$tong bài học"
    }
}
