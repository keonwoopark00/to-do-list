package com.edgar.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgar.todolist.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(
    private var title: String = "title",
) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    lateinit var onButtonClick: (String) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetBinding.inflate(inflater, container, false)

        // fill TextViews with right information
        binding.bsTvTitle.text = title

        binding.bsBtn.setOnClickListener { onButtonClick(binding.bsEtName.text.toString()) }

        return binding.root
    }
}