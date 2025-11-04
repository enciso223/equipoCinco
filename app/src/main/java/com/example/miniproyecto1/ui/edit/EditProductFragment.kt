package com.example.miniproyecto1.ui.editproduct

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.miniproyecto1.R
import com.example.miniproyecto1.databinding.FragmentEditProductBinding

class EditProductFragment : Fragment(R.layout.fragment_edit_product) {

    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditProductBinding.bind(view)

        binding.btnSaveChanges.setOnClickListener {
            findNavController().navigate(R.id.action_editProductFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
