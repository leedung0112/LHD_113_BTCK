package com.example.btck_lhd_113.repository

import com.example.btck_lhd_113.model.*
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()

    // Lấy danh sách bài học (sắp xếp theo id)
    fun getBaiHoc(onDone: (List<BaiHocModel>) -> Unit) {
        db.collection("bai_hoc").get()
            .addOnSuccessListener { snapshot ->
                val list = mutableListOf<BaiHocModel>()
                for (doc in snapshot.documents) {
                    try {
                        val id = try { doc.getLong("id")?.toInt() ?: doc.getLong("id_bai_hoc")?.toInt() ?: doc.getString("id")?.toInt() ?: 0 } catch(e:Exception){0}
                        val ten_bai = doc.getString("ten_bai") ?: ""
                        val so_tu = doc.getLong("so_tu")?.toInt() ?: 0
                        val da_mo = doc.getBoolean("da_mo") ?: false
                        list.add(BaiHocModel(id, ten_bai, so_tu, da_mo))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                onDone(list.sortedBy { it.id })
            }
    }

    // Lấy từ vựng theo ID bài học
    fun getTuVung(idBai: Int, onDone: (List<TuVungModel>) -> Unit) {
        db.collection("tu_vung").get()
            .addOnSuccessListener { snapshot ->
                val list = mutableListOf<TuVungModel>()
                for (doc in snapshot.documents) {
                    try {
                        val id_bai_hoc = try { doc.getLong("id_bai_hoc")?.toInt() ?: doc.getString("id_bai_hoc")?.toInt() ?: 0 } catch(e:Exception){0}
                        val tu = doc.getString("tu") ?: ""
                        val nghia = doc.getString("nghia") ?: ""
                        val phien_am = doc.getString("phien_am") ?: ""
                        val vi_du = doc.getString("vi_du") ?: ""
                        list.add(TuVungModel(0, id_bai_hoc, tu, nghia, phien_am, vi_du))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                var filtered = list.filter { it.id_bai_hoc == idBai }
                if (filtered.isEmpty()) {
                    filtered = list // Fallback if idBai doesn't match
                }
                onDone(filtered)
            }
            .addOnFailureListener { onDone(emptyList()) }
    }

    // Lấy câu hỏi theo ID bài học
    fun getCauHoi(idBai: Int, onDone: (List<CauHoiModel>) -> Unit) {
        db.collection("cau_hoi").get()
            .addOnSuccessListener { snapshot ->
                val list = mutableListOf<CauHoiModel>()
                for (doc in snapshot.documents) {
                    try {
                        val id_bai_hoc = try { doc.getLong("id_bai_hoc")?.toInt() ?: doc.getString("id_bai_hoc")?.toInt() ?: 0 } catch(e:Exception){0}
                        val cau_hoi = doc.getString("cau_hoi") ?: ""
                        val dap_an = doc.get("dap_an") as? List<String> ?: listOf()
                        val dap_an_dung = try { doc.getLong("dap_an_dung")?.toInt() ?: doc.getString("dap_an_dung")?.toInt() ?: 0 } catch(e:Exception){0}
                        val giai_thich = doc.getString("giai_thich") ?: ""
                        
                        list.add(CauHoiModel(id_bai_hoc, cau_hoi, dap_an, dap_an_dung, giai_thich))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                var filtered = list.filter { it.id_bai_hoc == idBai }
                if (filtered.isEmpty()) {
                    filtered = list // Fallback if idBai doesn't match
                }
                onDone(filtered)
            }
            .addOnFailureListener { onDone(emptyList()) }
    }

    // Lấy thông tin người dùng theo UID
    fun getUser(uid: String, onDone: (NguoiDungModel?) -> Unit) {
        db.collection("nguoi_dung").document(uid).get()
            .addOnSuccessListener { onDone(it.toObject(NguoiDungModel::class.java)) }
    }

    // Lưu hoặc cập nhật thông tin người dùng
    fun saveUser(user: NguoiDungModel, onDone: () -> Unit) {
        db.collection("nguoi_dung").document(user.uid).set(user)
            .addOnSuccessListener { onDone() }
    }

    // Cập nhật tiến độ bài học (Thêm ID bài học vào danh sách đã học)
    fun addProgress(uid: String, idBai: Int) {
        val ref = db.collection("nguoi_dung").document(uid)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(ref)
            val user = snapshot.toObject(NguoiDungModel::class.java)
            if (user != null) {
                val currentProgress = user.tien_do.toMutableList()
                if (!currentProgress.contains(idBai)) {
                    currentProgress.add(idBai)
                    transaction.update(ref, "tien_do", currentProgress)
                }
            }
            null
        }
    }
}
