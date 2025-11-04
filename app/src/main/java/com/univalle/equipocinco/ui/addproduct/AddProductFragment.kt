package com.univalle.equipocinco.ui.addproduct

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.univalle.equipocinco.R
import com.univalle.equipocinco.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment(R.layout.fragment_add_product) {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddProductBinding.bind(view)

        binding.btnGoHome.setOnClickListener {
            findNavController().navigate(R.id.action_addProductFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
