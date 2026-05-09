package com.example.btck_lhd_113.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.btck_lhd_113.R
import com.example.btck_lhd_113.model.TuVungModel

class DanhSachTuVungAdapter(private val dsTuVung: List<TuVungModel>) : RecyclerView.Adapter<DanhSachTuVungAdapter.TuVungViewHolder>() {

    class TuVungViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTuVung: TextView = itemView.findViewById(R.id.tvTuVung)
        val tvPhienAm: TextView = itemView.findViewById(R.id.tvPhienAm)
        val tvNghia: TextView = itemView.findViewById(R.id.tvNghia)
        val tvViDu: TextView = itemView.findViewById(R.id.tvViDu)
        val imgPhonetic: ImageView = itemView.findViewById(R.id.imgPhonetic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TuVungViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_danh_sach_tu_vung, parent, false)
        return TuVungViewHolder(view)
    }

    override fun onBindViewHolder(holder: TuVungViewHolder, position: Int) {
        val tuVung = dsTuVung[position]
        holder.tvTuVung.text = tuVung.tu
        holder.tvPhienAm.text = tuVung.phien_am
        holder.tvNghia.text = tuVung.nghia
        holder.tvViDu.text = tuVung.vi_du

        holder.imgPhonetic.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Phát âm: ${tuVung.tu}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return dsTuVung.size
    }
}
