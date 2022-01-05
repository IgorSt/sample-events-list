package com.igorsantos.listiningevents.view.eventDetails

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main) { eventDetailsViewModel.getEventDetails(args.data.id) }

        binding.data = args.data

        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            inflateMenu(R.menu.event_details_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.share -> {

                    }
                    R.id.checkin -> {
                        val action = EventDetailsFragmentDirections.actionEventDetailsFragmentToDialogCheckinBottomSheet(args.data)
                        findNavController().navigate(action)

                    }
                }
                true
            }
        }

        eventDetailsViewModel.eventDetails.observe(viewLifecycleOwner) {
            binding.apply {
                Glide.with(requireContext())
                    .load(it.image)
                    .centerCrop()
                    .error(R.drawable.image_not_found)
                    .into(binding.banner)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}