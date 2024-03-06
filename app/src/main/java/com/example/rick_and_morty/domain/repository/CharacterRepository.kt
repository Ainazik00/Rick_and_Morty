package com.example.rick_and_morty.domain.repository

import androidx.lifecycle.LiveData
import com.example.rick_and_morty.data.remote.dto.CharactersDto
import com.example.rick_and_morty.data.remote.dto.ResultDto

interface CharacterRepository {

     fun getAllCharacters(pageNumber: Int):LiveData<CharactersDto>

     fun getCharacterById(id: Int): LiveData<ResultDto>
}
