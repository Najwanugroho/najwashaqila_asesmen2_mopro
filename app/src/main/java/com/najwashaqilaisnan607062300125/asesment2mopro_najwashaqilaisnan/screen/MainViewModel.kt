package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import androidx.lifecycle.ViewModel
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan

class MainViewModel : ViewModel() {
    val data = listOf(
        Catatan(
            id = 1L,
            moodLevel = "Senang",
            deskripsi = "Hari ini cerah banget, aku jalan-jalan sore sambil denger lagu favorit.",
            tanggal = "2025-01-01 10:00:00"
        ),
        Catatan(
            id = 2L,
            moodLevel = "Sedih",
            deskripsi = "Entah kenapa merasa gloomy seharian, mungkin karena kurang tidur.",
            tanggal = "2025-01-02 11:45:00"
        ),
        Catatan(
            id = 3L,
            moodLevel = "Senang",
            deskripsi = "Banyak progress tugas hari ini, rasanya puas banget!",
            tanggal = "2025-01-03 09:30:00"
        ),
        Catatan(
            id = 4L,
            moodLevel = "Kesal",
            deskripsi = "Ada bug yang bikin pusing, tapi pelan-pelan mulai ngerti.",
            tanggal = "2025-01-04 14:15:00"
        ),
        Catatan(
            id = 5L,
            moodLevel = "Kesal",
            deskripsi = "Hari ini full kegiatan, rasanya pengen rebahan seharian.",
            tanggal = "2025-01-05 17:10:00"
        ),
        Catatan(
            id = 6L,
            moodLevel = "Senang",
            deskripsi = "Meditasi sebentar bikin pikiran lebih damai dan jernih.",
            tanggal = "2025-01-06 20:30:00"
        ),
        Catatan(
            id = 7L,
            moodLevel = "Kesal",
            deskripsi = "Ada hal kecil yang ngeselin, tapi belajar untuk sabar.",
            tanggal = "2025-01-07 07:20:00"
        )
    )
    fun getCatatan(id:Long):Catatan?{
        return data.find { it.id==id }
    }
}
