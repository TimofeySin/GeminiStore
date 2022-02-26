package com.example.geministore.ui.urovo

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geministore.R
import com.example.geministore.databinding.FragmentSettingUrovoBinding


class UrovoFragment : Fragment() {

    private var _binding: FragmentSettingUrovoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val urovoViewModel =
            ViewModelProvider(this)[UrovoViewModel::class.java]

        _binding = FragmentSettingUrovoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var urovoId = mutableListOf<String>()
        val urovoNameId = binding.urovoNameId


        if (urovoViewModel.bluetoothPermissionWasGranted(root.context)) {
            activity?.let { urovoViewModel.getBluetoothDeviceAdapter(it) }
            val spinnerUrovoName: Spinner = binding.spinnerUrovoName
            urovoViewModel.bluetoothDeviceList.observe(viewLifecycleOwner) { it ->
                spinnerUrovoName.adapter =
                    ArrayAdapter(requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        it)
            }
            urovoViewModel.urovoBluetoothAddress.observe(viewLifecycleOwner){ urovoId = it}

            spinnerUrovoName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    urovoNameId.text = urovoId[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        } else {
            urovoViewModel.getBluetoothDeviceAdapter(requireActivity())
        }








        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}