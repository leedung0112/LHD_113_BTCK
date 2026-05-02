package com.example.btck_lhd_113.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.TuVungModel
import com.example.btck_lhd_113.repository.FirebaseRepository

class HocTuVungActivity : AppCompatActivity() {

    private val repo = FirebaseRepository()
    private var dsTuVung = listOf<TuVungModel>()
    private var index = 0
    private var idBaiHoc = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_hoc_tu_vung)

        idBaiHoc = intent.getIntExtra("ID_BAI_HOC", 0)

        // Tải dữ liệu từ Repository
        repo.getTuVung(idBaiHoc) { list ->
            dsTuVung = list
            if (dsTuVung.isNotEmpty()) hienThi(index)
        }

        // Sự kiện nút "Tiếp theo"
        findViewById<TextView>(R.id.btnNext).setOnClickListener {
            if (index < dsTuVung.size - 1) {
                index++
                hienThi(index)
            } else {
                chuyenManHinhKetQua()
            }
        }

        // Các nút khác
        findViewById<ImageView>(R.id.btnSpeaker).setOnClickListener {
            Toast.makeText(this, "Phát âm: ${dsTuVung[index].tu}", Toast.LENGTH_SHORT).show()
        }
        findViewById<ImageView>(R.id.ivClose).setOnClickListener { finish() }
    }

    private fun hienThi(pos: Int) {
        findViewById<TextView>(R.id.tvWord).text = dsTuVung[pos].tu
        findViewById<TextView>(R.id.tvMeaning).text = dsTuVung[pos].nghia
    }

    private fun chuyenManHinhKetQua() {
        val intent = Intent(this, KetQuaActivity::class.java).apply {
            putExtra("DIEM_SO", dsTuVung.size)
            putExtra("TONG_SO", dsTuVung.size)
            putExtra("ID_BAI_HOC", idBaiHoc)
            putExtra("LOAI_BAI_TAP", "VOCAB")
        }
        startActivity(intent)
        finish()
    }
}
