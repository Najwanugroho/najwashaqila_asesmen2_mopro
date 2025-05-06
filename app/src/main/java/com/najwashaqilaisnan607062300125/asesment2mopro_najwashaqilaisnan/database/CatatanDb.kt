// database/CatatanDb.kt
package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.najwashaqilaisnan607062300125.asesment2_najwashaqilaisnan_mopro.database.CatatanDao
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan

@Database(entities = [Catatan::class], version = 2)
abstract class CatatanDb : RoomDatabase() {
    abstract val dao: CatatanDao

    companion object {
        @Volatile
        private var INSTANCE: CatatanDb? = null

        fun getInstance(context: Context): CatatanDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatatanDb::class.java,
                    "catatan.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE catatan ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE catatan ADD COLUMN deletedAt TEXT")
            }
        }
    }
}
