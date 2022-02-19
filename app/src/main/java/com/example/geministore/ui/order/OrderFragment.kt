package com.example.geministore.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geministore.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this)[OrderViewModel::class.java]

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val bundle = arguments
        bundle?.let {
            val idOrder = it.getString("idOrder")
            val date = it.getString("date")
            Log.d("Bundel", "$idOrder/$date") }

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}