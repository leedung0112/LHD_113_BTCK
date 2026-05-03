package com.example.btck_lhd_113.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.CauHoiModel
import com.example.btck_lhd_113.repository.FirebaseRepository

class TracNghiemActivity : AppCompatActivity() {

    private val repo = FirebaseRepository()
    private var listCH = listOf<CauHoiModel>()
    private var index = 0 // Vị trí câu hỏi hiện tại
    private var soCauDung = 0 // Số câu trả lời đúng
    private var idBai = 0
    private var daChon = -1 // Vị trí đáp án người dùng chọn (0=A, 1=B, 2=C, 3=D)
    private var daKiemTra = false // Cờ đánh dấu đã bấm nút Kiểm Tra chưa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_quiz)

        idBai = intent.getIntExtra("ID_BAI_HOC", 0)
        
        // Lấy danh sách câu hỏi từ Firebase
        repo.getCauHoi(idBai) { list ->
            listCH = list
            if (listCH.isNotEmpty()) {
                hienThi(index)
            } else {
                Toast.makeText(this, "Không có câu hỏi nào", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        setupButtons()
    }

    private fun hienThi(pos: Int) {
        daChon = -1
        daKiemTra = false
        val item = listCH[pos]
        
        // Hiển thị nội dung câu hỏi và tiến độ
        findViewById<TextView>(R.id.txtCauHoi).text = item.cau_hoi
        findViewById<TextView>(R.id.txtTienTrinh).text = "${pos + 1}/${listCH.size}"
        findViewById<ProgressBar>(R.id.thanhTienTrinh).max = listCH.size
        findViewById<ProgressBar>(R.id.thanhTienTrinh).progress = pos + 1 
        
        // Ẩn giải thích và đặt lại chữ cho nút Kiểm tra
        findViewById<CardView>(R.id.theGiaiThich).visibility = View.GONE
        findViewById<Button>(R.id.btnKiemTra).text = "Kiểm tra →"
        
        // Gán text cho 4 đáp án (luôn mặc định có 4 đáp án)
        findViewById<TextView>(R.id.txtDapAnA).text = item.dap_an[0]
        findViewById<TextView>(R.id.txtDapAnB).text = item.dap_an[1]
        findViewById<TextView>(R.id.txtDapAnC).text = item.dap_an[2]
        findViewById<TextView>(R.id.txtDapAnD).text = item.dap_an[3]

        // Ẩn icon đúng/sai lúc ban đầu
        findViewById<ImageView>(R.id.iconDungA).visibility = View.GONE
        findViewById<ImageView>(R.id.iconDungB).visibility = View.GONE
        findViewById<ImageView>(R.id.iconDungC).visibility = View.GONE
        findViewById<ImageView>(R.id.iconDungD).visibility = View.GONE

        // Lấy 4 vùng đáp án
        val vungA = findViewById<View>(R.id.vungDapAnA)
        val vungB = findViewById<View>(R.id.vungDapAnB)
        val vungC = findViewById<View>(R.id.vungDapAnC)
        val vungD = findViewById<View>(R.id.vungDapAnD)

        // Reset màu nền về mặc định ban đầu
        vungA.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)
        vungB.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)
        vungC.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)
        vungD.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)

        // Viết một hàm nhỏ để xử lý việc chọn đáp án cho ngắn gọn
        fun xuLyChonDapAn(viTri: Int, vungDuocChon: View) {
            if (!daKiemTra) { // Chỉ cho chọn khi chưa ấn Kiểm Tra
                daChon = viTri
                
                // Trả tất cả về nền mặc định
                vungA.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)
                vungB.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)
                vungC.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)
                vungD.setBackgroundResource(R.drawable.nen_dap_an_mac_dinh)
                
                // Riêng vùng được chọn thì đổi thành nền chọn (viền xanh nhạt)
                vungDuocChon.setBackgroundResource(R.drawable.nen_dap_an_chon)
            }
        }

        // Bắt sự kiện click cho 4 đáp án
        vungA.setOnClickListener { xuLyChonDapAn(0, vungA) }
        vungB.setOnClickListener { xuLyChonDapAn(1, vungB) }
        vungC.setOnClickListener { xuLyChonDapAn(2, vungC) }
        vungD.setOnClickListener { xuLyChonDapAn(3, vungD) }
    }

    private fun setupButtons() {
        // Nút đóng màn hình
        findViewById<ImageView>(R.id.btnDong).setOnClickListener { finish() }
        
        val btnKiemTra = findViewById<Button>(R.id.btnKiemTra)
        btnKiemTra.setOnClickListener {
            if (listCH.isEmpty()) return@setOnClickListener
            
            // Xử lý 2 trạng thái của nút:
            // 1. Trạng thái kiểm tra đáp án
            // 2. Trạng thái chuyển câu tiếp theo
            if (!daKiemTra) {
                // TRẠNG THÁI 1: KIỂM TRA ĐÁP ÁN
                
                if (daChon == -1) {
                    Toast.makeText(this, "Vui lòng chọn một đáp án!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                
                daKiemTra = true
                val item = listCH[index]
                val dapAnDung = item.dap_an_dung
                
                // Lấy lại các view
                val vungA = findViewById<View>(R.id.vungDapAnA)
                val vungB = findViewById<View>(R.id.vungDapAnB)
                val vungC = findViewById<View>(R.id.vungDapAnC)
                val vungD = findViewById<View>(R.id.vungDapAnD)
                
                val iconA = findViewById<ImageView>(R.id.iconDungA)
                val iconB = findViewById<ImageView>(R.id.iconDungB)
                val iconC = findViewById<ImageView>(R.id.iconDungC)
                val iconD = findViewById<ImageView>(R.id.iconDungD)

                // Hàm hỗ trợ tô màu đúng/sai cho dễ hiểu
                fun toMauDungSai(viTri: Int, vung: View, icon: ImageView) {
                    if (viTri == dapAnDung) {
                        // Đây là đáp án đúng -> Luôn tô viền xanh, hiện icon đúng
                        vung.setBackgroundResource(R.drawable.nen_dap_an_dung)
                        icon.setImageResource(R.drawable.bieu_tuong_dung)
                        icon.visibility = View.VISIBLE
                    } else if (viTri == daChon) {
                        // Đây là đáp án sai mà người dùng chọn -> Tô viền đỏ, hiện icon sai
                        vung.setBackgroundResource(R.drawable.nen_dap_an_sai)
                        icon.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
                        icon.visibility = View.VISIBLE
                    }
                }

                // Chạy hàm kiểm tra màu cho 4 đáp án
                toMauDungSai(0, vungA, iconA)
                toMauDungSai(1, vungB, iconB)
                toMauDungSai(2, vungC, iconC)
                toMauDungSai(3, vungD, iconD)

                // Tính điểm
                if (daChon == dapAnDung) {
                    soCauDung++
                }
                
                // Hiện thẻ giải thích nếu có
                val theGiaiThich = findViewById<CardView>(R.id.theGiaiThich)
                val txtGiaiThich = findViewById<TextView>(R.id.txtGiaiThich)
                if (item.giai_thich.isNotEmpty()) {
                    theGiaiThich.visibility = View.VISIBLE
                    txtGiaiThich.text = item.giai_thich
                }
                
                // Đổi chữ nút
                if (index < listCH.size - 1) {
                    btnKiemTra.text = "Tiếp tục →"
                } else {
                    btnKiemTra.text = "Xem kết quả →"
                }
                
            } else {
                // TRẠNG THÁI 2: CHUYỂN CÂU
                
                if (index < listCH.size - 1) {
                    index++ // Tăng chỉ mục câu hỏi
                    hienThi(index) // Hiển thị câu hỏi mới
                } else {
                    // Chuyển sang màn hình kết quả
                    val intent = Intent(this, KetQuaActivity::class.java)
                    intent.putExtra("DIEM_SO", soCauDung)
                    intent.putExtra("TONG_SO", listCH.size)
                    intent.putExtra("ID_BAI_HOC", idBai)
                    intent.putExtra("LOAI_BAI_TAP", "QUIZ")
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
