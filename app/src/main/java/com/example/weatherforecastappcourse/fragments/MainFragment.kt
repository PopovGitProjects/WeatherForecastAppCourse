package com.example.weatherforecastappcourse.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.weatherforecastappcourse.adapters.ViewPagerAdapter
import com.example.weatherforecastappcourse.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private val fragmentList = listOf(
        HourForecastFragment.newInstance(),
        DayForecastFragment.newInstance()
    )
    private val fragmentTitleList = listOf(
        "Hour",
        "Day"
    )
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var _pLauncher: ActivityResultLauncher<String>? = null
    private val pLauncher get() = _pLauncher!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()

    }
    private fun init() = with(binding){
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fragmentList)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2){
                tab, pos -> tab.text = fragmentTitleList[pos]

        }.attach()
    }

    private fun permissionListener(){
        _pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_SHORT)
                .show()
        }
    }
    private fun checkPermission(){
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _pLauncher = null
    }
}