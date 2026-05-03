package com.example.btck_lhd_113.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.BaiHocModel

class BaiHocAdapter(private val ds: List<BaiHocModel>, private val onClick: (BaiHocModel) -> Unit) :
    RecyclerView.Adapter<BaiHocAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val ten: TextView = v.findViewById(R.id.txtTenBaiHoc)
        val st: TextView = v.findViewById(R.id.txtTrangThai)
        val soTu: TextView = v.findViewById(R.id.txtSoTu)
        val btnHoc: View = v.findViewById(R.id.btnHoc)
        val list: View = v.findViewById(R.id.vungBaiHoc)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) = 
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_bai_hoc, p, false))

    override fun onBindViewHolder(h: VH, p: Int) {
        val item = ds[p]
        h.ten.text = item.ten_bai
        h.soTu.text = "${item.so_tu} từ"
        
        if (item.da_mo) {
            h.st.text = "SẴN SÀNG"
            h.st.setTextColor(Color.parseColor("#1D4ED8"))
            h.btnHoc.visibility = View.VISIBLE
            h.list.alpha = 1.0f
            h.list.setOnClickListener { onClick(item) }
        } else {
            h.st.text = "CHƯA MỞ KHÓA"
            h.st.setTextColor(Color.GRAY)
            h.btnHoc.visibility = View.GONE
            h.list.alpha = 0.6f
            h.list.setOnClickListener {
                // Có thể thông báo cho người dùng là bài học chưa mở
            }
        }
    }

    override fun getItemCount() = ds.size
}
