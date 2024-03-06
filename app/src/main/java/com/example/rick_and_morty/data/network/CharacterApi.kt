package com.example.rick_and_morty.data.network

import com.example.rick_and_morty.data.remote.dto.CharactersDto
import com.example.rick_and_morty.data.remote.dto.ResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") pageNumber: Int?
    ): CharactersDto

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): ResultDto
}