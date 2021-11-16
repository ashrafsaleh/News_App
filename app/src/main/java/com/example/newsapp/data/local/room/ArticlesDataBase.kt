package com.example.newsapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsapp.model.Article


@Database(entities = [Article::class],version = 5)
@TypeConverters(Converters::class)

abstract class ArticlesDataBase : RoomDatabase(){
    abstract fun articleDao(): ArticlesDao

    companion object {
        @Volatile
        private var INSTANCE: ArticlesDataBase? = null


        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        fun getAppDataBase(context: Context): ArticlesDataBase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<ArticlesDataBase>(
                    context.applicationContext, ArticlesDataBase::class.java, "AppDB"
                )
                    .addMigrations(migration_1_2)
                               .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }

    }
}