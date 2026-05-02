package com.example.btck_lhd_113.model

data class NguoiDungModel(
    val uid: String = "",
    val hoten: String = "",
    val email: String = "",
    val tien_do: List<Int> = listOf() // Danh sách id_bai_hoc đã hoàn thành
)
