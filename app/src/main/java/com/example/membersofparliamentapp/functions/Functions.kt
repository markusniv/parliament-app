package com.example.membersofparliamentapp.functions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.model.Member

/**     (c) Markus Nivasalo, 16.9.2021
 *
 *      Universal functions used in multiple classes.
 */

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

// Get complete name of a member's party
fun getPartyName(memb : Member) : String {
    return when(memb.party) {
        "kd" -> "Kristillisdemokraatit"
        "kesk" -> "Keskusta"
        "kok" -> "Kokoomus"
        "liik" -> "Liike Nyt"
        "ps" -> "Perussuomalaiset"
        "r" -> "Suomen ruotsalainen kansanpuolue"
        "sd" -> "Sosiaalidemokraatit"
        "vihr" -> "Vihreät"
        else -> "Vasemmistoliitto"
    }
}

/*
  !!NOT MINE!!

  Function used for hiding the keyboard when EditText not active, used in the BaseTextInputEditText-class,
  source found there.
 */
fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}