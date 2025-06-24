package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengguna")
data class Pengguna(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val email: String,
    val password: String
)
