package com.phattarapong.koin.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.phattarapong.koin.model.asLocalList
import com.phattarapong.koin.network.ApiService
import com.phattarapong.koin.network.Result
import com.phattarapong.templatemvvm.database.CharacterDao
import com.phattarapong.templatemvvm.database.CharacterLocal
import kotlinx.coroutines.flow.collect

interface MainRepository {
    suspend fun refreshCharacterList()
}

class MainRepositoryImpl(private val dao: CharacterDao, private val apiService: ApiService) :
    MainRepository {

    private val _characterList = MutableLiveData<Result<List<CharacterLocal>>>()
    val characterList = _characterList

    override suspend fun refreshCharacterList() {
        _characterList.value = Result.Loading

        try {
            dao.insertAll(apiService.getCharacters().results!!.asLocalList())
        } catch (e: Exception) {
            _characterList.value = Result.Error(e.message!!)
        }

        try {
            dao.getAll().asFlow().collect {
                _characterList.value = Result.Success(it)
            }
        } catch (e: Exception) {
            _characterList.value = Result.Error(e.message!!)
        }
    }

}