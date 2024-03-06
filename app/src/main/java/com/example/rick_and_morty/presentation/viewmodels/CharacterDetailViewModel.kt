package com.example.rick_and_morty.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.rick_and_morty.domain.use_cases.GetCharacterByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {

    private var characterId: Int? = null

    fun setCharacterId(id: Int?) {
        characterId = id
    }

    fun getCharacterById(it: Int?) {
        characterId = it
    }

    val newCharacterDetail = liveData {
        val result = characterId?.let { id ->
            withContext(Dispatchers.IO) {
                getCharacterByIdUseCase.invoke(id)
            }
        }
        emit(result)
    }
}
