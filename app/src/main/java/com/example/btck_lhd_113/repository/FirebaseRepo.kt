package com.example.btck_lhd_113.repository

import com.example.btck_lhd_113.model.*
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()

    // Lấy danh sách bài học (sắp xếp theo id)
    fun getBaiHoc(onDone: (List<BaiHocModel>) -> Unit) {
        db.collection("bai_hoc").orderBy("id").get()
            .addOnSuccessListener { onDone(it.toObjects(BaiHocModel::class.java)) }
    }

    // Lấy từ vựng theo ID bài học
    fun getTuVung(idBai: Int, onDone: (List<TuVungModel>) -> Unit) {
        db.collection("tu_vung").whereEqualTo("id_bai_hoc", idBai).get()
            .addOnSuccessListener { onDone(it.toObjects(TuVungModel::class.java)) }
    }

    // Lấy câu hỏi theo ID bài học
    fun getCauHoi(idBai: Int, onDone: (List<CauHoiModel>) -> Unit) {
        db.collection("cau_hoi").whereEqualTo("id_bai_hoc", idBai).get()
            .addOnSuccessListener { onDone(it.toObjects(CauHoiModel::class.java)) }
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
