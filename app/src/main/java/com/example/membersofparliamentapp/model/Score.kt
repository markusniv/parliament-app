package com.example.membersofparliamentapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** (c) Markus Nivasalo, 6.10.2021
 *
 *      Data class for the scores of each parliament member.
 */

@Entity(tableName = "score_table")
data class Score(
    @PrimaryKey
    val personNumber: Int,
    val score: Int
)
