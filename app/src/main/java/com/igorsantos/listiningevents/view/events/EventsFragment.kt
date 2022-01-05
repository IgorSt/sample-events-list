package com.igorsantos.listiningevents.view.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.igorsantos.listiningevents.arq.lifecycle.EventObserver
import com.igorsantos.listiningevents.databinding.FragmentEventsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    val binding get() = _binding!!

    private val eventsViewModel: EventsViewModel by viewModels()
    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        eventsAdapter = EventsAdapter(eventsViewModel)

        return FragmentEventsBinding.inflate(inflater, container, false).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main) { eventsViewModel.getEvents() }

        binding.eventsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventsAdapter
        }

        eventsViewModel.eventsList.observe(viewLifecycleOwner) {
            eventsAdapter.submitList(it)
        }

        eventsViewModel.onEventsDetailsClicked.observe(viewLifecycleOwner, EventObserver{
            val action = EventsFragmentDirections.actionEventsFragmentToEventDetailsFragment(it)
            findNavController().navigate(action)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}