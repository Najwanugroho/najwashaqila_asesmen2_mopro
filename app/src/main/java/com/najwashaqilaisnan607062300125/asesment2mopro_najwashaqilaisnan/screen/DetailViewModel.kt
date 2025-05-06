package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najwashaqilaisnan607062300125.asesment2_najwashaqilaisnan_mopro.database.CatatanDao
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: CatatanDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(moodLevel: String, deskripsi: String) {
        val catatan = Catatan(
            tanggal = formatter.format(Date()),
            moodLevel = moodLevel,
            deskripsi = deskripsi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(catatan)
        }
    }

    suspend fun getCatatan(id: Long): Catatan? {
        return dao.getCatatanById(id)
    }

    fun update(id: Long, moodLevel: String, deskripsi: String) {
        val catatan = Catatan(
            id = id,
            tanggal = formatter.format(Date()),
            moodLevel = moodLevel,
            deskripsi = deskripsi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(catatan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}
