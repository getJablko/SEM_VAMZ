package com.example.sem_nova.GUI.ItemDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.GUI.NewOrderScreen.ItemDetails
import com.example.sem_nova.GUI.NewOrderScreen.toItem
import com.example.sem_nova.GUI.NewOrderScreen.toItemDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * vievmodel pre správu poloziek/itemov
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */
class ItemDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: DataRepository,
) : ViewModel() {

    private val name: String = checkNotNull(savedStateHandle[ItemDetailDestination.itemName])

    /**
     * uchováva UI stav itemu
     */
    val uiState: StateFlow<ItemDetailsUiState> =
        itemsRepository.getItemStream(name)
            .filterNotNull()
            .map {
                ItemDetailsUiState(outOfStock = it.quantity <= 0, itemDetails = it.toItemDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsUiState()
            )

    /**
     * predanie 1 ks itemu
     */
    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.value.itemDetails.toItem()
            if (currentItem.quantity > 0) {
                itemsRepository.upsertItem(currentItem)
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state pre ItemDetailsScreen
 */
data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val itemDetails: ItemDetails = ItemDetails()
)