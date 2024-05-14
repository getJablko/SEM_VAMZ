package com.example.sem_nova.GUI.ReceivedOrderScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.GUI.NewOrderScreen.OrderDetails
import com.example.sem_nova.GUI.NewOrderScreen.toOrderDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel pre označovanie objednávok za doručené.
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */

class ReceiveOrderViewModel(
    private val orderRepository: DataRepository,
) : ViewModel() {

    // počiatočné ID objednávky - pre funkčnost viewmodelu
    private var _orderId by mutableStateOf(0)

    // UI State na zaznamenávanie OrderDetailsUiState
    private var _orderDetailsUiState = mutableStateOf(OrderDetailsUiState())

    /**
     * funkcia na aktualizovanie ID objednávky
     */
    fun updateOrderId(newOrderId: Int) {
        _orderId = newOrderId
        loadOrderDetails(newOrderId)
    }

    /**
     * funkcia na načítanie údajov o objednávke na základe jej ID
     */
    private fun loadOrderDetails(orderId: Int) {
        viewModelScope.launch {
            orderRepository.getOrderStream(orderId)
                .map { order ->
                    // order može byt null - počiatočne ID objednávky
                    order?.let {
                        OrderDetailsUiState(
                            arrived = it.arrived,
                            orderDetails = it.toOrderDetails()
                        )
                    }
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                    initialValue = OrderDetailsUiState()
                ).collect {
                    if (it != null) {
                        _orderDetailsUiState.value = it
                    }
                }
        }
    }

    /**
     * funkcia na označenie objednávky za doručenú a aktualizovanie skladu
     */
    fun markOrderAsDeliveredAndUpdateStorage(deliveredOrderId: Int) {
        viewModelScope.launch {
            // najdenie objednavky na zaklade zadaného ID
            val deliveredOrder = orderRepository.getOrderStream(deliveredOrderId).firstOrNull()
            // oznacenie objednavky za dorucenu
            if (deliveredOrder != null) {
                orderRepository.updateOrder(deliveredOrder.copy(arrived = true))
            }
            // aktualizovanie skaldu
            if (deliveredOrder != null) {
                orderRepository.updateStorage(deliveredOrder)
            }
        }
    }

    /**
     * funkcia na zistenie, či je zadané ID správne (existencia objednávky)
     * vracia hodnoty true/false
     */
    fun isOrderValid(deliveredOrderId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val deliveredOrder = orderRepository.getOrderStream(deliveredOrderId).firstOrNull()

            if ((deliveredOrder != null && deliveredOrder.arrived == true) || deliveredOrder == null) {
                callback(false)
            } else {
                callback(true)
            }
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