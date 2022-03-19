package com.igorsantos.listiningevents.view.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.igorsantos.listiningevents.R
import com.igorsantos.listiningevents.databinding.DialogCheckinBottomSheetBinding
import com.igorsantos.listiningevents.rules.emailValidator
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
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFinish.setOnClickListener {

            val isValidForm = form()

            if (isValidForm) {
                val name = binding.txtName.text.toString()
                val email = binding.txtEmail.text.toString()

                dialogCheckinViewModel.apply {
                    setup(args.data.id, name, email)
                }

                Toast.makeText(requireContext(), getString(R.string.congrats), Toast.LENGTH_SHORT)
                    .show()
                dismiss()
            }
        }
    }

    private fun form(): Boolean {
        if (binding.txtName.text.isNullOrEmpty()) {
            binding.txtName.setError(getString(R.string.name_cannot_empty))
            return false
        }
        if (!binding.txtEmail.text.toString().trim().emailValidator()) {
            binding.txtEmail.setError(getString(R.string.invalid_email))
            return false
        }
        return true
    }
}