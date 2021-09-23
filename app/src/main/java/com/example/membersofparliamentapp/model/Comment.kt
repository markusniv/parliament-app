package com.example.membersofparliamentapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class Comment (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val personNumber : Int,
    val content : String
)