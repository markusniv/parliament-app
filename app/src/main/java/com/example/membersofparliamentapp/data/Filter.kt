package com.example.membersofparliamentapp.data

class Filter {
    var currentStatus: Status = Status.NONE
    var currentParty: String? = null
    var currentSearch: String? = null

}

enum class Status {
    NONE, PARTY, SEARCH
}