package com.khan.assesment.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khan.assesment.models.Result


@Database(entities = [Result::class],version = 2)
abstract class roomDataBaseClass:RoomDatabase() {

    companion object{
        val DATABASE_NAME = "response_api"
        var databaseInstance : roomDataBaseClass ? =null


        fun getDatabaseInstance(context: Context):roomDataBaseClass{

            if(databaseInstance == null){

                synchronized(roomDataBaseClass ::class.java){

                    databaseInstance = Room.databaseBuilder(
                        context.applicationContext, roomDataBaseClass::class.java,
                        DATABASE_NAME
                    ).build()


                }



            }
            return databaseInstance!!

        }
    }

    abstract fun responseDao() : ResponseDao


}