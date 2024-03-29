package com.example.rick_and_morty.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.homework_2_6m.R
import com.example.homework_2_6m.databinding.FragmentCharacterDetailBinding
import com.example.rick_and_morty.presentation.viewmodels.CharacterDetailViewModel
import com.example.rick_and_morty.util.autoCleared

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    private var binding: FragmentCharacterDetailBinding by autoCleared()
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCharacterDetailBinding.bind(view)

        val characterId = arguments?.getInt("characterId")

        characterId?.let {
            characterDetailViewModel.getCharacterById(it)
        }

        showProgressBar()
        fetchingData()
        hideProgressBar()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchingData() {
        activity?.let {
            characterDetailViewModel.newCharacterDetail
                .observe(viewLifecycleOwner) { character ->
                    binding.nameDetail.text = character.name
                    binding.genderDetail.text = character.gender.toString()
                    binding.dimensionDetail.text =  binding.dimensionDetail.text
                    binding.originDetail.text = character.origin?.name
                    binding.locationDetail.text = character.location?.name
                    binding.typeDetail.text = character.type
                    binding.episodesDetail.text = character.episode?.size.toString()
                    binding.let {
                        binding.characterImageDetail.load(character.image) {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    }
                    binding.characterSpeciesAndStatusDetail.text =
                        "${character.status} - ${character.species}"
                    if (character.status?.contains("Dead") == true) {
                        binding.colorIndicatorDetail.setImageResource(R.drawable.ic_circle_red)
                    } else if (character.status?.contains("Alive") == true) {
                        binding.colorIndicatorDetail.setImageResource(R.drawable.ic_circle_green)
                    } else binding.colorIndicatorDetail.setImageResource(R.drawable.ic_circle_grey)
                }
        }
    }

    private fun hideProgressBar() {
        binding.progressBarDetail.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBarDetail.visibility = View.VISIBLE
    }
}
