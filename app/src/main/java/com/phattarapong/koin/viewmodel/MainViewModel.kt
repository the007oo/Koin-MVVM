package com.phattarapong.koin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phattarapong.koin.repository.MainRepositoryImpl
import kotlinx.coroutines.launch

class MainViewModel(mainRepositoryImpl: MainRepositoryImpl) : ViewModel() {

    var characterList = mainRepositoryImpl.characterList

    init {
        viewModelScope.launch {
            mainRepositoryImpl.refreshCharacterList()
        }
    }
}