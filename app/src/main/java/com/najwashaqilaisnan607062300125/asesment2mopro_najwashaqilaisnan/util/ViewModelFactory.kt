package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.najwashaqilaisnan607062300125.asesment2_najwashaqilaisnan_mopro.database.CatatanDao
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.DetailViewModel
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen.MainViewModel


class ViewModelFactory (
    private val dao :CatatanDao): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T: ViewModel>create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dao) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}