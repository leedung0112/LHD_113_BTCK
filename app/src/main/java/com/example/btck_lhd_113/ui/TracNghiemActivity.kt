package com.example.btck_lhd_113.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.CauHoiModel
import com.example.btck_lhd_113.repository.FirebaseRepository

class TracNghiemActivity : AppCompatActivity() {

    private val repo = FirebaseRepository()
    private var listCH = listOf<CauHoiModel>()
    private var index = 0
    private var soCauDung = 0
    private var idBai = 0
    private var daChon = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_quiz)

        idBai = intent.getIntExtra("ID_BAI_HOC", 0)
        repo.getCauHoi(idBai) { list ->
            listCH = list
            if (listCH.isNotEmpty()) hienThi(index)
        }

        setupButtons()
    }

    private fun hienThi(pos: Int) {
        daChon = -1
        val item = listCH[pos]
        findViewById<TextView>(R.id.txtCauHoi).text = item.cau_hoi
        findViewById<TextView>(R.id.txtTienTrinh).text = "${pos + 1}/${listCH.size}"
        findViewById<ProgressBar>(R.id.thanhTienTrinh).apply { max = listCH.size; progress = pos + 1 }
        
        // Reset giao diện các đáp án (A, B, C, D)
        val vungDapAn = listOf<View>(findViewById(R.id.vungDapAnA), findViewById(R.id.vungDapAnB), findViewById(R.id.vungDapAnC), findViewById(R.id.vungDapAnD))
        vungDapAn.forEachIndexed { i, view ->
            view.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)
            view.setOnClickListener { 
                daChon = i
                vungDapAn.forEach { it.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh) }
                view.setBackgroundResource(R.drawable.nen_dap_an_chon)
            }
        }
    }

    private fun setupButtons() {
        findViewById<ImageView>(R.id.btnDong).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnKiemTra).setOnClickListener {
            if (daChon == -1) return@setOnClickListener
            
            val dung = listCH[index].dap_an_dung
            if (daChon == dung) soCauDung++
            
            if (index < listCH.size - 1) {
                index++; hienThi(index)
            } else {
                val intent = Intent(this, KetQuaActivity::class.java).apply {
                    putExtra("DIEM_SO", soCauDung); putExtra("TONG_SO", listCH.size)
                    putExtra("ID_BAI_HOC", idBai); putExtra("LOAI_BAI_TAP", "QUIZ")
                }
                startActivity(intent); finish()
            }
        }
    }
}
