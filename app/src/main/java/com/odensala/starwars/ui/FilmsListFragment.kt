package com.odensala.starwars.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.odensala.starwars.R
import com.odensala.starwars.adapter.FilmsAdapter
import com.odensala.starwars.databinding.FragmentFilmsListBinding
import com.odensala.starwars.util.Resource

class FilmsListFragment : Fragment(R.layout.fragment_films_list) {

    private var _binding: FragmentFilmsListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val TAG = "FilmsListFragment"
    lateinit var filmsAdapter: FilmsAdapter
    private val filmsViewModel: FilmsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFilmsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        navigateWithBundle()

        filmsViewModel.films.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { filmsResponse ->
                        filmsAdapter.submitList(filmsResponse.results)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        filmsAdapter = FilmsAdapter()
        binding.recyclerViewMovieList.apply {
            adapter = filmsAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
            setHasFixedSize(true)
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }

    /**
     * onClickListener with safeargs bundle
     */
    private fun navigateWithBundle() {
        filmsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("resultBundle", it)
            }
            findNavController().navigate(
                R.id.action_movieListFragment_to_detailFragment,
                bundle
            )
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}