package com.igorsantos.listiningevents.view.events

import android.app.AlertDialog
import android.content.DialogInterface
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
        lifecycle.addObserver(eventsViewModel)
        eventsAdapter = EventsAdapter(eventsViewModel)

        return FragmentEventsBinding.inflate(inflater, container, false).also {
            _binding = it
            binding.viewModel = eventsViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventsAdapter
        }

        eventsViewModel.apply {
            eventsList.observe(viewLifecycleOwner) {
                eventsAdapter.submitList(it)
            }

            onEventsDetailsClicked.observe(viewLifecycleOwner, EventObserver{
                val action = EventsFragmentDirections.actionEventsFragmentToEventDetailsFragment(it)
                findNavController().navigate(action)
            })

            errorLoad.observe(viewLifecycleOwner) {
                val build = AlertDialog.Builder(requireContext())
                build.setMessage("Lamento, ocorreu um erro inesperado: $it")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener {_, _ ->
                        requireActivity().onBackPressed() }).show()
            }

            loading.observe(viewLifecycleOwner) {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                binding.eventsRecycler.visibility = if (it) View.GONE else View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}