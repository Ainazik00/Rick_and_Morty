package com.example.rick_and_morty.domain.use_cases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.rick_and_morty.data.remote.dto.CharactersDto
import com.example.rick_and_morty.domain.models.Characters
import com.example.rick_and_morty.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    fun invoke(charactersDto: CharactersDto): LiveData<Characters?> = liveData(Dispatchers.IO) {
        try {
            val characters = charactersDto.toCharacter()
            emit(characters)
        } catch (e: Exception) {
            emit(null)
        }
    }
}
