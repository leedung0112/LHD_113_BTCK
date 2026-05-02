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
        val list: View = v.findViewById(R.id.vungBaiHoc)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) = 
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_bai_hoc, p, false))

    override fun onBindViewHolder(h: VH, p: Int) {
        val item = ds[p]
        h.ten.text = item.ten_bai
        h.st.text = if (item.da_mo) "SẴN SÀNG" else "CHƯA MỞ KHÓA"
        h.st.setTextColor(if (item.da_mo) Color.BLUE else Color.GRAY)
        h.list.setOnClickListener { if (item.da_mo) onClick(item) }
    }

    override fun getItemCount() = ds.size
}
