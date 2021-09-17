package com.example.membersofparliamentapp.functions

import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.model.Member

// Get a drawable id of a member's party's logo
fun getPartyColor(memb : Member) : Int {
    return when(memb.party) {
        "kd" -> R.color.kristilliset
        "kesk" -> R.color.keskusta
        "kok" -> R.color.kokoomus
        "liik" -> R.color.liike_nyt
        "ps" -> R.color.persu
        "r" -> R.color.ruotsalaiset
        "sd" -> R.color.demarit
        "vihr" -> R.color.vihreat
        else -> R.color.vasemmisto
    }
}