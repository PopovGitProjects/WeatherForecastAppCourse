package com.example.weatherforecastappcourse.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.databinding.FragmentConverterBinding
import com.example.weatherforecastappcourse.domain.CalcPress


class ConverterFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding){
            translateButton.setOnClickListener{
                if (!editTextLeft.text.isNullOrEmpty() && editTextLeft.isFocused){
                    editTextRight.setText(
                        CalcPress()
                        .calculatePressure(editTextLeft, Const.MMHG_CONST))
                }
                if (!editTextRight.text.isNullOrEmpty() && editTextRight.isFocused){
                    editTextLeft.setText(
                        CalcPress()
                        .calculatePressure(editTextRight, Const.PA_CONST))
                }
            }
            editTextLeft.addTextChangedListener {
                if (editTextLeft.text.isNullOrEmpty() && editTextLeft.isFocused){
                    editTextRight.setText("")
                }
            }
            editTextRight.addTextChangedListener {
                if (editTextRight.text.isNullOrEmpty() && editTextRight.isFocused){
                    editTextLeft.setText("")
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConverterFragment()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}