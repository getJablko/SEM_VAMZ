package com.example.sem_nova.GUI.OrderScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.Data.Order
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class OrderViewModel(private val dataRepository: DataRepository) : ViewModel() {



    val orderUiState: StateFlow<OrderUiState> =
        dataRepository.getAllOrdersStream().map { orders -> OrderUiState(orderList = orders) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = OrderUiState()
            )


    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}

data class OrderUiState(
    val orderList: List<Order> = listOf()
)