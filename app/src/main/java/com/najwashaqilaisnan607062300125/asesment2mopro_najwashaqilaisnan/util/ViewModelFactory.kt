package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.najwashaqilaisnan607062300125.asesment2_najwashaqilaisnan_mopro.database.CatatanDao
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.MainViewModel


class ViewModelFactory(
    private val dao: CatatanDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
