package com.igorsantos.listiningevents.view.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.igorsantos.listiningevents.databinding.DialogCheckinBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogCheckinBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: DialogCheckinBottomSheetBinding

    private val dialogCheckinViewModel: DialogCheckinViewModel by viewModels()
    private val args: DialogCheckinBottomSheetArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogCheckinBottomSheetBinding.inflate(inflater, container, false).also {
            binding = it
            binding.viewModel = dialogCheckinViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFinish.setOnClickListener {
            val name = binding.txtName.text.toString()
            val email = binding.txtEmail.text.toString()

            dialogCheckinViewModel.setup(args.data.id, name, email)
        }
    }
}