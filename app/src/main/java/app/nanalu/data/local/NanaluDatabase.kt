package app.nanalu.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.nanalu.data.local.dao.ChildProfileDao
import app.nanalu.data.local.dao.SleepEpisodeDao
import app.nanalu.data.local.entity.ChildProfileEntity
import app.nanalu.data.local.entity.SleepEpisodeEntity

@Database(
    entities = [ChildProfileEntity::class, SleepEpisodeEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class NanaluDatabase : RoomDatabase() {
    abstract fun childProfileDao(): ChildProfileDao
    abstract fun sleepEpisodeDao(): SleepEpisodeDao

    companion object {
        const val NAME = "nanalu.db"

        /** Banco no armazenamento privado do app. Nunca usar destrutive migration: dados não podem sumir. */
        fun create(context: Context): NanaluDatabase =
            Room.databaseBuilder(context.applicationContext, NanaluDatabase::class.java, NAME).build()
    }
}
