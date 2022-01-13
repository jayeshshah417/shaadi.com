package com.jayesh.shadi.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "profile", indices = [Index(value = ["Email"], unique = true)])
public class ProfileTable{


    @PrimaryKey(autoGenerate = true)
    private var id: Int? = null

    fun getId(): Int? {
        return id
    }

    fun setId(_id: Int?) {
        this.id = _id
    }

    @ColumnInfo(name = "Id Value")
    private var id_value: String? = null

    fun getId_value(): String? {
        return id_value
    }

    fun setId_value(_id: String?) {
        this.id_value = _id
    }
    @ColumnInfo(name = "Email")
    private var email: String? = null

    fun getEmail(): String? {
        return email
    }

    fun setEmail(_id: String?) {
        this.email = _id
    }

    @ColumnInfo(name = "First")
    private var first: String? = null

    fun getFirst(): String? {
        return first
    }

    fun setFirst(_id: String?) {
        this.first = _id
    }

    @ColumnInfo(name = "status")
    private var status: String? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(_id: String?) {
        this.status = _id
    }
    @ColumnInfo(name = "large")
    private var large: String? = null

    fun getLarge(): String? {
        return large
    }

    fun setLarge(_id: String?) {
        this.large = _id
    }

}

