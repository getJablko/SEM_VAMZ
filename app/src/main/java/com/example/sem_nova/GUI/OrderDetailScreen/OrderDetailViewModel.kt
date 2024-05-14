package com.example.sem_nova.GUI.OrderDetailScreen


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.Data.Order
import com.example.sem_nova.GUI.NewOrderScreen.OrderDetails
import com.example.sem_nova.GUI.NewOrderScreen.toOrderDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 * ViewModel na zobrazenie udajov o objednavkach
 */

class OrderDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val orderRepository: DataRepository,
) : ViewModel() {

    // ziskanie ID objednavky z [OrderDetailDestination.orderId]
    private val orderId: Int = checkNotNull(savedStateHandle[OrderDetailDestination.orderId])

    /**
     * Order UI state na základe ID objednavky
     */
    val uiState: StateFlow<OrderDetailsUiState> =
        orderRepository.getOrderStream(orderId)
            .filterNotNull()
            .map {
                OrderDetailsUiState(
                    arrived = it.arrived,
                    orderDetails = it.toOrderDetails()
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = OrderDetailsUiState()
            )

    /**
     * Označenie objednavky za doručenú a aktualizácia skladu
     */
    fun markOrderAsDeliveredAndUpdateStorage(deliveredOrder: Order) {
        viewModelScope.launch {
            // označenie objednavky za dorucenu
            orderRepository.updateOrder(deliveredOrder.copy(arrived = true))
            // aktualizovanie skladu
            orderRepository.updateStorage(deliveredOrder)
        }
    }
    /**
     * nastavenie timeoutu na 5ms
     */
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state pre OrderDetailsContent
 */
data class OrderDetailsUiState(
    val arrived: Boolean = false,
    val orderDetails: OrderDetails = OrderDetails()
)