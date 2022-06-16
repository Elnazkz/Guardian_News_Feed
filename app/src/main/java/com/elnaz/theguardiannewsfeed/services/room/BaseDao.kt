package com.elnaz.theguardiannewsfeed.services.room

import androidx.room.*

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(data: List<T>)

    @Update
    abstract suspend fun update(data: T)

    @Delete
    abstract suspend fun delete(data: T)
}