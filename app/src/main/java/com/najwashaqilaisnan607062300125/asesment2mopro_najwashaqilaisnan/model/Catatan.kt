package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catatan")
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val moodLevel: String,
    val deskripsi: String,
    val tanggal: String,
    val isDeleted: Boolean = false,
    val deletedAt: String? = null
)
