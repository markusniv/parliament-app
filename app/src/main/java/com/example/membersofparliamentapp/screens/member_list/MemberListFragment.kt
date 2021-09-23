package com.example.membersofparliamentapp.screens.member_list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.membersofparliamentapp.MainActivity
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.adapters.MemberListAdapter
import com.example.membersofparliamentapp.data.Filter
import com.example.membersofparliamentapp.data.Status
import com.example.membersofparliamentapp.databinding.FragmentMemberListBinding
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.viewmodel.MemberViewModel
import com.example.membersofparliamentapp.viewmodel.MemberViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

private lateinit var binding: FragmentMemberListBinding
private lateinit var adapter: MemberListAdapter

class MemberListFragment : Fragment() {

    private val mMemberViewModel : MemberViewModel by viewModels {
        MemberViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)

        adapter = MemberListAdapter()
        val recyclerView = binding.memberListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mMemberViewModel.addMembers()
        getList()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)  {
            R.id.filterMenu -> {
                PopupMenu(
                    MyApp.appContext,
                    binding.anchorMenu,
                    Gravity.RIGHT
                ).apply {
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.selectParty -> {
                                PopupMenu(
                                    MyApp.appContext,
                                    binding.anchorMenu,
                                    Gravity.RIGHT
                                ).apply {
                                    setOnMenuItemClickListener { party ->
                                        mMemberViewModel.currentFilter.currentStatus = Status.PARTY
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
                            R.id.selectPerson -> {
                                getList()
                                true
                            }
                            else -> false
                        }
                    }
                    inflate(R.menu.dropdown_filters)
                    show()
                }
            }
            R.id.filterRestore -> {
                resetList()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun getList() {
        val filter = mMemberViewModel.currentFilter

        when (filter.currentStatus) {
            Status.PARTY -> {
                filter.currentParty?.let {
                    mMemberViewModel.filterByParty(it).observe(viewLifecycleOwner, { member ->
                        adapter.setData(member)
                    })
                }
            }
            Status.NONE -> {
                mMemberViewModel.readAllData.observe(viewLifecycleOwner, { member ->
                    adapter.setData(member)
                })
            }
        }
    }

    private fun setPartyGetList(party : String) {
        mMemberViewModel.currentFilter.currentParty = party
        getList()
    }

    private fun resetList() {
        mMemberViewModel.currentFilter.currentParty = null
        mMemberViewModel.currentFilter.currentStatus = Status.NONE
        getList()
    }

}