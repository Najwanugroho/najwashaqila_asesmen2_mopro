package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import androidx.lifecycle.ViewModel
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database.PenggunaDao
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Pengguna

class LoginViewModel(private val penggunaDao: PenggunaDao) : ViewModel() {

    suspend fun login(email: String, password: String): Pengguna? {
        return penggunaDao.login(email, password)
    }

    suspend fun register(nama: String, email: String, password: String) {
        penggunaDao.insert(Pengguna(nama = nama, email = email, password = password))
    }
}
