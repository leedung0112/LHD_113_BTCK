package com.example.btck_lhd_113.ui

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.adapter.BaiHocAdapter
import com.example.btck_lhd_113.repository.FirebaseRepository

class TrangchuActivity : AppCompatActivity() {

    private val repo = FirebaseRepository()
    private lateinit var rvBaiHoc: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_trang_chu)

        // 1. Ánh xạ & Setup List
        rvBaiHoc = findViewById(R.id.rvBaiHoc)
        rvBaiHoc.layoutManager = LinearLayoutManager(this)

        // 2. Load dữ liệu từ Repo
        repo.getBaiHoc { list ->
            rvBaiHoc.adapter = BaiHocAdapter(list) { baiHoc ->
                val intent = Intent(this, HocTuVungActivity::class.java)
                intent.putExtra("ID_BAI_HOC", baiHoc.id)
                intent.putExtra("TEN_BAI", baiHoc.ten_bai)
                startActivity(intent)
            }
        }

        // 3. Nút điều hướng dưới cùng
        findViewById<LinearLayout>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, HosoActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.btnTienDo).setOnClickListener {
            startActivity(Intent(this, TienDoActivity::class.java))
        }
    }
}
