package com.example.rick_and_morty.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.rick_and_morty.data.remote.dto.CharactersDto
import com.example.rick_and_morty.data.remote.dto.ResultDto
import com.example.rick_and_morty.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.HttpException
import java.io.IOException

class CharacterRepositoryImpl (
    private val characterApi: CharacterApi,
    private val dispatcherIO: CoroutineDispatcher
) : CharacterRepository {

    override fun getAllCharacters(pageNumber: Int): LiveData<CharactersDto> =
        liveData(dispatcherIO) {
            try {
                val result = characterApi.getAllCharacters(pageNumber)
                emit(result)
            } catch (e: IOException) {
                println(e.message)
            } catch (e: HttpException) {
                println(e.message)

            }
        }

    override fun getCharacterById(id: Int): LiveData<ResultDto> = liveData(dispatcherIO) {
        try {
            val result = characterApi.getCharacterById(id)
            emit(result)
        } catch (e: IOException) {
            println(e.message)
        } catch (e: HttpException) {
            println(e.message)
        }
    }
}