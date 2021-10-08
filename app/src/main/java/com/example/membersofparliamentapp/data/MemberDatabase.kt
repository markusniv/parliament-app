package com.example.membersofparliamentapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.model.Score

/**     (c) Markus Nivasalo, 16.9.2021
 *
 *      RoomDB class for the database itself.
 */

@Database(entities = [Member::class, Comment::class, Score::class], version = 1, exportSchema = false)
abstract class MemberDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    companion object {
        @Volatile
        private var INSTANCE: MemberDatabase? = null

        fun getDatabase(context: Context): MemberDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemberDatabase::class.java,
                    "member_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}