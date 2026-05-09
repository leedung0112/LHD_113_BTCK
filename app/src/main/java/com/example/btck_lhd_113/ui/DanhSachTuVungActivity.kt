package com.example.btck_lhd_113.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.adapter.DanhSachTuVungAdapter
import com.example.btck_lhd_113.model.TuVungModel
import com.example.btck_lhd_113.repository.FirebaseRepository

class DanhSachTuVungActivity : AppCompatActivity() {

    private lateinit var rvTuVung: RecyclerView
    private lateinit var imgBack: ImageView
    private val repo = FirebaseRepository()
    private var dsTuVung = listOf<TuVungModel>()
    private var idBaiHoc = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_danh_sach_tu_vung)

        rvTuVung = findViewById(R.id.rvTuVung)
        imgBack = findViewById(R.id.imgBack)

        // Setup RecyclerView
        rvTuVung.layoutManager = LinearLayoutManager(this)

        idBaiHoc = intent.getIntExtra("ID_BAI_HOC", 0)

        // Tải dữ liệu từ Repository
        repo.getTuVung(idBaiHoc) { list ->
            dsTuVung = list
            if (dsTuVung.isNotEmpty()) {
                val adapter = DanhSachTuVungAdapter(dsTuVung)
                rvTuVung.adapter = adapter
            } else {
                Toast.makeText(this, "Không có từ vựng cho bài học này", Toast.LENGTH_SHORT).show()
            }
        }

        // Sự kiện back
        imgBack.setOnClickListener {
            finish()
        }
    }
}
