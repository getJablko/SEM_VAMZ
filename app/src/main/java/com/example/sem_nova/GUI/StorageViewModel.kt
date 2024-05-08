package com.example.sem_nova.GUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.Item
import com.example.sem_nova.Data.DataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class StorageViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val storageUiState: StateFlow<StorageUiState> =
        dataRepository.getAllItemsStream().map { StorageUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = StorageUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class StorageUiState(val itemList: List<Item> = listOf())