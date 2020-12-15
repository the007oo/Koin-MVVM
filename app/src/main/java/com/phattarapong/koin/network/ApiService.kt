package com.phattarapong.koin.network

import com.phattarapong.koin.model.CharacterRemote
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    suspend fun getCharacters(): CharacterRemote
}