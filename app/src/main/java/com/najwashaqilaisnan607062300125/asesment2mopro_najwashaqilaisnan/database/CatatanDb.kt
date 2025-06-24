package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Catatan
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Pengguna

@Database(entities = [Catatan::class, Pengguna::class], version = 3)
abstract class CatatanDb : RoomDatabase() {
    abstract val dao: CatatanDao
    abstract val penggunaDao: PenggunaDao

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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE catatan ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE catatan ADD COLUMN deletedAt TEXT")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS pengguna (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        nama TEXT NOT NULL,
                        email TEXT NOT NULL,
                        password TEXT NOT NULL
                    )
                """.trimIndent())
            }
        }
    }
}
