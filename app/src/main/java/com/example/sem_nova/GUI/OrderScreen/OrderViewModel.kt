package com.example.sem_nova.GUI.OrderScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.Data.Order
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 *
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 * ViewModel na získanie vsetkych objednávok z Room databázy
 */

class OrderViewModel(private val dataRepository: DataRepository) : ViewModel() {

    /**
     * zoznam objednávok z [DataRepository] namapovanýh na [OrderUiState]
     * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
     */
    val orderUiState: StateFlow<OrderUiState> =
        dataRepository.getAllOrdersStream().map { orders -> OrderUiState(orderList = orders) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = OrderUiState()
            )

    /**
     * nastavenie timeoutu na 5ms
     */
    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}
/**
 * UI State pre OrderUiState
 */
data class OrderUiState(
    val orderList: List<Order> = listOf()
)