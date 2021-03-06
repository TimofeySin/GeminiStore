package ru.timofeysin.geministore.ui.urovo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.timofeysin.geministore.databinding.FragmentSettingUrovoBinding

class UrovoFragment : Fragment() {

    private var _binding: FragmentSettingUrovoBinding? = null

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


        if (urovoViewModel.bluetoothPermissionWasGranted(root.context)) {
            activity?.let { urovoViewModel.getBluetoothDeviceAdapter(it) }
            val spinnerUrovoName: Spinner = binding.spinnerUrovoName
            urovoViewModel.bluetoothDeviceList.observe(viewLifecycleOwner) { it ->
                spinnerUrovoName.adapter =
                    ArrayAdapter(requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        it)
            }
            val urovoId: TextView = binding.urovoId
            urovoViewModel.deviceId.observe(viewLifecycleOwner) { urovoId.text = it }


            urovoViewModel.devicePos.observe(viewLifecycleOwner) { spinnerUrovoName.setSelection(it) }




            spinnerUrovoName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    urovoViewModel.saveToSharedPreference(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        } else {
            urovoViewModel.getBluetoothDeviceAdapter(requireActivity())
        }




        urovoViewModel.getFromSharedPref()



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}