package com.elnaz.theguardiannewsfeed.di

import android.content.Context
import androidx.room.Room
import com.elnaz.theguardiannewsfeed.services.room.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext ctx: Context): AppDataBase {
        val builder = Room.databaseBuilder(ctx, AppDataBase::class.java, "articles_db")
            .allowMainThreadQueries()
        return builder.build()
    }


}