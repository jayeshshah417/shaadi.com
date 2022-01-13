package com.jayesh.shadi.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.jayesh.shadi.model.Profile

@Dao
abstract class ProfileTableDao {

    @Query("Select * from `profile` where id > :lastId order by id limit :limit")
    abstract fun getCategory(limit:Int,lastId:Int): LiveData<MutableList<ProfileTable>>?

    @Query("Select `Email` from `profile`")
    abstract fun getProfileIds(): MutableList<String>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(users: ProfileTable?): Long

    @Query("update `profile` set status = :status where `Email` = :email")
    abstract fun update(status:String?,email:String?)

    @Delete
    abstract fun delete(users: ProfileTable?): Int
}