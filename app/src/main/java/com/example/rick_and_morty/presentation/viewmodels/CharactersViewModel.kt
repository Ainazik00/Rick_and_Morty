package com.example.rick_and_morty.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.rick_and_morty.domain.use_cases.GetCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private var charactersPage = 1

    val newCharacters = liveData {
        val result = withContext(Dispatchers.IO) {
            getCharactersUseCase.invoke(charactersPage)
        }
        emit(result)
    }

    fun nextPage() {
        charactersPage++
    }
}
