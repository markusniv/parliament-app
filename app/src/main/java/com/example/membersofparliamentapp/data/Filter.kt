package com.example.membersofparliamentapp.data

/**     (c) Markus Nivasalo, 23.9.2021
 *
 *      File containing filter classes for the MemberListViewModel, affecting which members are shown
 *      in the MemberListFragment.
 */


class Filter {
    var currentStatus: Status = Status.NONE
    var currentParty: String? = null
    var currentSearch: String? = null

}

enum class Status {
    NONE, PARTY, SEARCH
}