package com.example.membersofparliamentapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "member_table")
data class Member (
    @PrimaryKey
    val personNumber: Int,
    val seatNumber: Int = 0,
    val last: String,
    val first: String,
    val party: String,
    val minister: Boolean = false,
    val picture: String = "",
    val twitter: String = "",
    val bornYear: Int = 0,
    val constituency: String = ""
)