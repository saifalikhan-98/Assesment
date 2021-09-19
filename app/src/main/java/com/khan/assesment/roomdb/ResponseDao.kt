package com.khan.assesment.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khan.assesment.models.Result

@Dao
interface ResponseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun saveResponse(result : Result) : Long


    @Query("SELECT * FROM postapi")
    fun getResponse():Result
}