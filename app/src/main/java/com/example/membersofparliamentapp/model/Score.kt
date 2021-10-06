package com.example.membersofparliamentapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** 6.10.2021
 *
 */

@Entity(tableName = "score_table")
data class Score(
    @PrimaryKey
    val personNumber: Int,
    val score: Int
)
