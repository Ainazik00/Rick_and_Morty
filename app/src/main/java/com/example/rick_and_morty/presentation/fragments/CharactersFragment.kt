package com.example.rick_and_morty.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_6m.R
import com.example.homework_2_6m.databinding.FragmentCharactersBinding
import com.example.rick_and_morty.domain.adapters.CharacterAdapter
import com.example.rick_and_morty.presentation.viewmodels.CharactersViewModel
import com.example.rick_and_morty.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.rick_and_morty.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters) {

    private var binding: FragmentCharactersBinding by autoCleared()
    private val charactersViewModel: CharactersViewModel by viewModels()
    private var charactersAdapter: CharacterAdapter? = null

    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCharactersBinding.bind(view)

        showProgressBar()
        setUpRecyclerView()

        charactersAdapter?.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("character", it)
            }
            findNavController().navigate(R.id.action_charactersFragment_to_characterDetailFragment, bundle)

        }
        fetchingData()
    }

    private fun fetchingData() {
        activity?.let {
            charactersViewModel.newCharacters
                .observe(viewLifecycleOwner) {
                    charactersAdapter?.differ?.submitList(it.results)
                    hideProgressBar()
                }
        }
    }


    private fun setUpRecyclerView() {
        charactersAdapter = CharacterAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(this@CharactersFragment.scrollListener)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                charactersViewModel.nextPage()
                isScrolling = false
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

}