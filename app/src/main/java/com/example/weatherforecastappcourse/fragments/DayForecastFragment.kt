package com.example.weatherforecastappcourse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastappcourse.OnClickItemListener
import com.example.weatherforecastappcourse.adapters.RecyclerViewAdapter
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.FragmentDayForecastBinding
import com.example.weatherforecastappcourse.models.WeatherModel
import com.example.weatherforecastappcourse.models.viewmodels.MainViewModel

class DayForecastFragment : Fragment(), OnClickItemListener {

    private var _binding: FragmentDayForecastBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerViewAdapter? = null
    private val model: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = DayForecastFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayForecastBinding.inflate(inflater, container, false)
        model.livesDayList.observe(viewLifecycleOwner){
            adapter?.submitList(it.subList(1, it.size))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding){
        recyclerViewDay.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerViewAdapter(
            this@DayForecastFragment,
            null,
            Const.DAY,
            requireContext()
        )
        recyclerViewDay.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickItem(item: WeatherModel) {
        model.liveCurrentData.value = item
    }
}