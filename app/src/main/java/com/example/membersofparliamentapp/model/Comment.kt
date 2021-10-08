package com.example.membersofparliamentapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** (c) Markus Nivasalo, 23.9.2021
 *
 *      Data class for the comments made of each parliament member.
 */

@Entity(tableName = "comment_table")
data class Comment (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val personNumber : Int,
    val content : String
)