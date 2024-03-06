package com.example.rick_and_morty.domain.use_cases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.rick_and_morty.data.remote.dto.ResultDto
import com.example.rick_and_morty.domain.models.ResultById
import com.example.rick_and_morty.domain.repository.CharacterRepository
import java.lang.Exception


class GetCharacterByIdUseCase(
    private val repository: CharacterRepository
) {
    fun invoke(characterDto: ResultDto): LiveData<ResultById?> = liveData {
        try {
            val characterResult = characterDto.toResultById()
            emit(characterResult)
        } catch (e: Exception) {
            emit(null)
        }
    }
}

