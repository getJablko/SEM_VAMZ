package com.example.sem_nova.GUI.OrderDetailScreen


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.Data.Item
import com.example.sem_nova.Data.Order
import com.example.sem_nova.GUI.ItemDetailScreen.ItemDetailDestination
import com.example.sem_nova.GUI.ItemDetailScreen.ItemDetailsUiState
import com.example.sem_nova.GUI.NewOrderScreen.ItemDetails
import com.example.sem_nova.GUI.NewOrderScreen.OrderDetails
import com.example.sem_nova.GUI.NewOrderScreen.toItem
import com.example.sem_nova.GUI.NewOrderScreen.toItemDetails
import com.example.sem_nova.GUI.NewOrderScreen.toOrder
import com.example.sem_nova.GUI.NewOrderScreen.toOrderDetails
import com.example.sem_nova.GUI.OrderScreen.OrderUiState
import com.example.sem_nova.GUI.OrderScreen.OrderViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val orderRepository: DataRepository,
) : ViewModel() {

    private val orderId: Int = checkNotNull(savedStateHandle[OrderDetailDestination.orderId])

    /**
     * Holds the order details ui state. The data is retrieved from [DataRepository] and mapped to
     * the UI state.
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
     * Reduces the order quantity by one and update the [DataRepository]'s data source.
     */
    fun markOrderAsArrived() {
        viewModelScope.launch {
            val currentOrder = uiState.value.orderDetails.toOrder()
            if (currentOrder.arrived == true) {
                orderRepository.updateOrder(currentOrder.copy(arrived = true))
            }
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