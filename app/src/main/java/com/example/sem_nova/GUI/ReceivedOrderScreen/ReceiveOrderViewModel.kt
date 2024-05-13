package com.example.sem_nova.GUI.ReceivedOrderScreen

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.Data.Order
import com.example.sem_nova.GUI.NewOrderScreen.OrderDetails
import com.example.sem_nova.GUI.NewOrderScreen.toOrderDetails
import com.example.sem_nova.GUI.OrderDetailScreen.OrderDetailDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue

import com.example.sem_nova.GUI.OrderDetailScreen.OrderDetailDestination.orderId
import com.example.sem_nova.GUI.OrderScreen.OrderViewModel.Companion.TIMEOUT_MILLIS
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReceiveOrderViewModel(
    private val orderRepository: DataRepository,
) : ViewModel() {
    private var _orderId by mutableStateOf(0)  // Initial invalid orderId


    // UI State to observe order details
    private var _orderDetailsUiState = mutableStateOf(OrderDetailsUiState())
    val orderDetailsUiState: State<OrderDetailsUiState> = _orderDetailsUiState


    // Method to update orderId
    fun updateOrderId(newOrderId: Int) {
        _orderId = newOrderId
        loadOrderDetails(newOrderId)
    }

    private fun loadOrderDetails(orderId: Int) {
        viewModelScope.launch {
            orderRepository.getOrderStream(orderId)
                .map { order ->
                    // Assuming that order could be null, handle that with a meaningful UI state
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
     * Holds the order details ui state. The data is retrieved from [DataRepository] and mapped to
     * the UI state.
     */
    /*
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


     */
    /**
     * Mark the order as arrived and update the [DataRepository]'s data source.
     */
    fun markOrderAsDeliveredAndUpdateStorage(deliveredOrderId: Int) {
        viewModelScope.launch {
            val deliveredOrder = orderRepository.getOrderStream(deliveredOrderId).firstOrNull()
            if (deliveredOrder != null) {
                orderRepository.updateOrder(deliveredOrder.copy(arrived = true))
            }
            // Trigger an update of the storage data for the delivered order
            if (deliveredOrder != null) {
                orderRepository.updateStorage(deliveredOrder)
            }
        }
    }

    fun isOrderValid(deliveredOrderId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val deliveredOrder = orderRepository.getOrderStream(deliveredOrderId).firstOrNull()

            if ((deliveredOrder != null && deliveredOrder.arrived == true) || deliveredOrder == null) {
                callback(false)
            } else {callback(true)}
        }
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for OrderDetailsScreen
 */
data class OrderDetailsUiState(
    val arrived: Boolean = false,
    val orderDetails: OrderDetails = OrderDetails()
)