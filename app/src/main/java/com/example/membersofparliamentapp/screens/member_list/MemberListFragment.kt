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
import com.example.membersofparliamentapp.databinding.FragmentMemberListBinding
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.viewmodel.MemberViewModel
import com.example.membersofparliamentapp.viewmodel.MemberViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

private lateinit var binding: FragmentMemberListBinding

class MemberListFragment : Fragment() {

    private val mMemberViewModel : MemberViewModel by viewModels {
        MemberViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)

        mMemberViewModel.addMembers()
        getAllMembers()
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
                                        when (party.itemId) {
                                            R.id.filterKd -> {
                                                getList("party", "kd")
                                                true
                                            }
                                            R.id.filterKesk -> {
                                                getList("party", "kesk")
                                                true
                                            }
                                            R.id.filterKok -> {
                                                getList("party", "kok")
                                                true
                                            }
                                            R.id.filterLiik -> {
                                                getList("party", "liik")
                                                true
                                            }
                                            R.id.filterPs -> {
                                                getList("party", "ps")
                                                true
                                            }
                                            R.id.filterRkp -> {
                                                getList("party", "r")
                                                true
                                            }
                                            R.id.filterSd -> {
                                                getList("party", "sd")
                                                true
                                            }
                                            R.id.filterVihr -> {
                                                getList("party", "vihr")
                                                true
                                            }
                                            R.id.filterVas -> {
                                                getList("party", "vas")
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
                                getList("test", "none")
                                true
                            }
                            else -> false
                        }
                    }
                    inflate(R.menu.dropdown_filters)
                    show()
                }
            }
            R.id.filterRestore -> getAllMembers()

        }
        return super.onOptionsItemSelected(item)
    }


    private fun getList(filter : String, party : String) {

        val adapter = MemberListAdapter()
        val recyclerView = binding.memberListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        when (filter) {
            "party" -> {
                mMemberViewModel.filterByParty(party).observe(viewLifecycleOwner, { member ->
                    adapter.setData(member)
                })
            }
            "test" -> {
                mMemberViewModel.readAllDataByParty.observe(viewLifecycleOwner, { member ->
                    adapter.setData(member)
                })
            }
            else -> {
                getAllMembers()
            }
        }
    }

    private fun getAllMembers() {
        val adapter = MemberListAdapter()
        val recyclerView = binding.memberListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mMemberViewModel.readAllData.observe(viewLifecycleOwner, { member ->
            adapter.setData(member)
        })
    }

}