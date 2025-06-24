package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Pengguna

@Dao
interface PenggunaDao {
    @Insert
    suspend fun insert(pengguna: Pengguna)

    @Query("SELECT * FROM pengguna WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Pengguna?
}

