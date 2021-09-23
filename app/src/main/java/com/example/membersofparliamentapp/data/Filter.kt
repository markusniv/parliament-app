package com.example.membersofparliamentapp.data

class Filter {
    var currentStatus: Status = Status.NONE
    var currentParty: String? = null

}

enum class Status {
    NONE, PARTY
}