package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database

import androidx.room.*
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan
import kotlinx.coroutines.flow.Flow

@Dao
interface CatatanDao {
    @Insert
    suspend fun insert(catatan: Catatan)

    @Update
    suspend fun update(catatan: Catatan)

    @Query("SELECT * FROM catatan WHERE isDeleted = 0 ORDER BY tanggal DESC")
    fun getCatatan(): Flow<List<Catatan>>

    @Query("SELECT * FROM catatan WHERE id = :id")
    suspend fun getCatatanById(id: Long): Catatan?

    @Query("DELETE FROM catatan WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE catatan SET isDeleted = 1, deletedAt = :timestamp WHERE id = :id")
    suspend fun softDeleteById(id: Long, timestamp: String)

    @Query("UPDATE catatan SET isDeleted = 0, deletedAt = null WHERE id = :id")
    suspend fun restoreById(id: Long)

    @Query("SELECT * FROM catatan WHERE isDeleted = 1 ORDER BY deletedAt DESC")
    fun getDeletedItems(): Flow<List<Catatan>>

    @Query("DELETE FROM catatan WHERE isDeleted = 1 AND deletedAt < :expiredTime")
    suspend fun permanentDeleteExpired(expiredTime: String)
}
