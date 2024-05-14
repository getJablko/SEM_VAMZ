package com.example.sem_nova.GUI.StorageScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.Data.Item
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 *
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 * ViewModel na získanie vsetkych itemov z Room databázy
 */
class StorageViewModel(private val dataRepository: DataRepository) : ViewModel() {

    /**
     * zoznam itemov z [DataRepository] namapovanýh na [StorageUiState]
     * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
     */
    val storageUiState: StateFlow<StorageUiState> =
        dataRepository.getAllItemsStream().map { StorageUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = StorageUiState()
            )

    /**
     * nastavenie timeoutu na 5ms
     *
     */
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
/**
 * UI State pre StorageContent
 */
data class StorageUiState(val itemList: List<Item> = listOf())