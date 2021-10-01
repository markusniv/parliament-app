package com.example.membersofparliamentapp.screens.settings

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.databinding.FragmentSettingsBinding

private lateinit var binding: FragmentSettingsBinding

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.settingsDeleteCache.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Are you sure you wish to delete your image cache?")
            builder.setTitle("Confirm")
            builder.setCancelable(true)
            builder.setPositiveButton("Yes") { _, _ ->
                deleteCache()
                updateUi()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }

        updateUi()
        return binding.root
    }

    private fun getCacheSize(): Double {
        try {
            val cacheDir = MyApp.appContext.cacheDir
            return cacheDir.walkTopDown().filter { it.isFile }.map { it.length() }.sum()
                .toDouble() / 1000
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0.0
    }

    private fun deleteCache() {
        try {
            val cacheDir = MyApp.appContext.cacheDir
            cacheDir.deleteRecursively()
        } catch (e : Exception) {
            e.printStackTrace()
        }

    }

    private fun updateUi() {
        val cacheSizeText = binding.settingsCacheSizeText
        cacheSizeText.text = "cache size: ${getCacheSize()}kb"
    }

}