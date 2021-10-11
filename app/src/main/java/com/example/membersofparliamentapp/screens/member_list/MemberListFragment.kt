package com.example.membersofparliamentapp.screens.member_list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.adapters.MemberListAdapter
import com.example.membersofparliamentapp.data.Status
import com.example.membersofparliamentapp.databinding.FragmentMemberListBinding
import com.example.membersofparliamentapp.viewmodel.MemberListViewModel
import com.example.membersofparliamentapp.viewmodel.MemberListViewModelFactory

/**     (c) Markus Nivasalo, 16.9.2021
 *
 *      The main fragment of the application, displaying a list of parliament members in a
 *      RecyclerView. Also includes drop-down menus in the toolbar for moving to settings and
 *      filtering the members, as well as search option so the user can search by mp names.
 */

private lateinit var binding: FragmentMemberListBinding
private lateinit var adapter: MemberListAdapter

class MemberListFragment : Fragment() {

    private val mMemberListViewModel : MemberListViewModel by viewModels {
        MemberListViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mMemberListViewModel.addScores()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)

        adapter = MemberListAdapter()
        val recyclerView = binding.memberListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        getList()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter, menu)

        val search = menu.findItem(R.id.searchByName)
        val searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.
        SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    setSearchGetList(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    setSearchGetList(newText)
                }
                return true
            }
        })
    }

    // Handles creating the various popup menus and setting filters accordingly.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)  {
            R.id.menuDropdown-> {
                PopupMenu(
                    MyApp.appContext,
                    binding.anchorMenu,
                    Gravity.END
                ).apply {
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menuFilters -> {
                                PopupMenu(
                                    MyApp.appContext,
                                    binding.anchorMenu,
                                    Gravity.END
                                ).apply {
                                    setOnMenuItemClickListener { item ->
                                        when (item.itemId) {
                                            R.id.selectParty -> {
                                                PopupMenu(
                                                    MyApp.appContext,
                                                    binding.anchorMenu,
                                                    Gravity.END
                                                ).apply {
                                                    setOnMenuItemClickListener { party ->
                                                        mMemberListViewModel.currentFilter.currentStatus = Status.PARTY
                                                        when (party.itemId) {
                                                            R.id.filterKd -> {
                                                                setPartyGetList("kd")
                                                                true
                                                            }
                                                            R.id.filterKesk -> {
                                                                setPartyGetList("kesk")
                                                                true
                                                            }
                                                            R.id.filterKok -> {
                                                                setPartyGetList("kok")
                                                                true
                                                            }
                                                            R.id.filterLiik -> {
                                                                setPartyGetList("liik")
                                                                true
                                                            }
                                                            R.id.filterPs -> {
                                                                setPartyGetList("ps")
                                                                true
                                                            }
                                                            R.id.filterRkp -> {
                                                                setPartyGetList("r")
                                                                true
                                                            }
                                                            R.id.filterSd -> {
                                                                setPartyGetList("sd")
                                                                true
                                                            }
                                                            R.id.filterVihr -> {
                                                                setPartyGetList("vihr")
                                                                true
                                                            }
                                                            R.id.filterVas -> {
                                                                setPartyGetList("vas")
                                                                true
                                                            }
                                                            else -> false
                                                        }
                                                    }
                                                    inflate(R.menu.dropdown_parties)
                                                    show()
                                                }
                                                true
                                            }
                                            R.id.selectConstituency -> {
                                                PopupMenu(
                                                    MyApp.appContext,
                                                    binding.anchorMenu,
                                                    Gravity.END
                                                ).apply {
                                                    setOnMenuItemClickListener { constituency ->
                                                        mMemberListViewModel.currentFilter.currentStatus = Status.CONSTITUENCY
                                                        when (constituency.itemId) {
                                                            R.id.filterHelsinki -> {
                                                                setConstituencyGetList("Helsinki")
                                                                true
                                                            }
                                                            R.id.filterUusi -> {
                                                                setConstituencyGetList("Uusimaa")
                                                                true
                                                            }
                                                            R.id.filterVars -> {
                                                                setConstituencyGetList("Varsinais-Suomi")
                                                                true
                                                            }
                                                            R.id.filterSata -> {
                                                                setConstituencyGetList("Satakunta")
                                                                true
                                                            }
                                                            R.id.filterAhv -> {
                                                                setConstituencyGetList("Ahvenanmaa")
                                                                true
                                                            }
                                                            R.id.filterHame -> {
                                                                setConstituencyGetList("Häme")
                                                                true
                                                            }
                                                            R.id.filterPirk -> {
                                                                setConstituencyGetList("Pirkanmaa")
                                                                true
                                                            }
                                                            R.id.filterKaak -> {
                                                                setConstituencyGetList("Kaakkois-Suomi")
                                                                true
                                                            }
                                                            R.id.filterSavo -> {
                                                                setConstituencyGetList("Savo-Karjala")
                                                                true
                                                            }
                                                            R.id.filterVaasa -> {
                                                                setConstituencyGetList("Vaasa")
                                                                true
                                                            }
                                                            R.id.filterKeski -> {
                                                                setConstituencyGetList("Keski-Suomi")
                                                                true
                                                            }
                                                            R.id.filterOulu -> {
                                                                setConstituencyGetList("Oulu")
                                                                true
                                                            }
                                                            R.id.filterLappi -> {
                                                                setConstituencyGetList("Lappi")
                                                                true
                                                            }
                                                            else -> false
                                                        }
                                                    }
                                                    inflate(R.menu.dropdown_constituency)
                                                    show()
                                                }
                                                true
                                            }
                                            else -> false
                                        }
                                    }
                                    inflate(R.menu.dropdown_filters)
                                    show()
                                }
                                true
                            }

                            R.id.menuSettings -> {
                                val action = MemberListFragmentDirections.actionMemberListFragmentToSettingsFragment()
                                findNavController().navigate(action)
                                true
                            }
                            else -> false
                        }
                    }
                    inflate(R.menu.dropdown_menu)
                    show()
                }
            }
            R.id.filterRestore -> {
                resetList()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    // Sets the RecyclerView's member list according to the current filters.
    private fun getList() {
        val filter = mMemberListViewModel.currentFilter

        when (filter.currentStatus) {
            Status.PARTY -> {
                filter.currentParty?.let {
                    mMemberListViewModel.filterByParty(it).observe(viewLifecycleOwner, { member ->
                        adapter.setData(member)
                    })
                }
            }
            Status.SEARCH -> {
                filter.currentSearch?.let {
                    mMemberListViewModel.filterByName(it).observe(viewLifecycleOwner, { member ->
                        adapter.setData(member)
                    })
                }
            }
            Status.CONSTITUENCY -> {
                filter.currentConstituency?.let {
                    mMemberListViewModel.filterByConstituency(it).observe(viewLifecycleOwner, { member ->
                        adapter.setData(member)
                    })
                }
            }
            Status.NONE -> {
                mMemberListViewModel.readAllData().observe(viewLifecycleOwner, { member ->
                    adapter.setData(member)
                })
            }
        }
    }

    private fun setPartyGetList(party : String) {
        mMemberListViewModel.currentFilter.currentParty = party
        getList()
    }
    private fun setConstituencyGetList(constituency : String) {
        mMemberListViewModel.currentFilter.currentConstituency = constituency
        getList()
    }
    private fun setSearchGetList(search : String) {
        mMemberListViewModel.currentFilter.currentStatus = Status.SEARCH
        val searchText = "%$search%"
        mMemberListViewModel.currentFilter.currentSearch = searchText
        getList()
    }

    private fun resetList() {
        Log.i("reset", "Reset should've happened")
        mMemberListViewModel.currentFilter.currentStatus = Status.NONE
        mMemberListViewModel.currentFilter.currentParty = null
        mMemberListViewModel.currentFilter.currentSearch = null
        mMemberListViewModel.currentFilter.currentConstituency = null
        getList()
    }

}