package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database.CatatanDao
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(private val dao: CatatanDao) : ViewModel() {

    val data: StateFlow<List<Catatan>> = dao.getCatatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun restore(id: Long) {
        viewModelScope.launch {
            dao.restoreById(id)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            dao.softDeleteById(id, timestamp)
        }
    }
}

