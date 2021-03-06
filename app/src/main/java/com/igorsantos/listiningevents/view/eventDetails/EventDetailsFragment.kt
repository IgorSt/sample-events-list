package com.igorsantos.listiningevents.view.eventDetails

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.igorsantos.listiningevents.R
import com.igorsantos.listiningevents.databinding.FragmentEventsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventsDetailsBinding? = null
    val binding get() = _binding!!

    private val args: EventDetailsFragmentArgs by navArgs()
    private val eventDetailsViewModel: EventsDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEventsDetailsBinding.inflate(inflater, container, false).also {
            _binding = it
            binding.viewModel = eventDetailsViewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventDetailsViewModel.getEventDetails(args.data.id)

        binding.data = args.data

        binding.toolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.share -> {
                        share()
                    }
                    R.id.checkin -> {
                        val action =
                            EventDetailsFragmentDirections.actionEventDetailsFragmentToDialogCheckinBottomSheet(
                                args.data
                            )
                        findNavController().navigate(action)

                    }
                }
                true
            }
        }

        eventDetailsViewModel.apply {
            eventDetails.observe(viewLifecycleOwner) {
                binding.apply {
                    Glide.with(requireContext())
                        .load(it.image)
                        .centerCrop()
                        .error(R.drawable.image_not_found)
                        .into(binding.banner)
                }
            }

            errorLoad.observe(viewLifecycleOwner) {
                val build = AlertDialog.Builder(requireContext())
                build.setMessage("Lamento, ocorreu um erro inesperado: $it")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                        requireActivity().onBackPressed() }).show()
            }
        }
    }

    fun share() {
        val send: Intent = Intent().apply {
            val text = eventDetailsViewModel.setDetails()
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(send, null)
        startActivity(shareIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}