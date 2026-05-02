package com.example.btck_lhd_113.model

data class CauHoiModel(
    val id_bai_hoc: Int = 0,
    val cau_hoi: String = "",
    val dap_an: List<String> = listOf(),
    val dap_an_dung: Int = 0,
    val giai_thich: String = ""
)
