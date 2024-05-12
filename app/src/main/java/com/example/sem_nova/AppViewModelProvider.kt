package com.example.sem_nova

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sem_nova.GUI.ItemDetailScreen.ItemDetailViewModel
import com.example.sem_nova.GUI.NewOrderScreen.NewOrderViewModel
import com.example.sem_nova.GUI.OrderDetailScreen.OrderDetailViewModel
import com.example.sem_nova.GUI.OrderScreen.OrderViewModel
import com.example.sem_nova.GUI.StorageScreen.StorageViewModel

/**
 * Provides Factory to create instance of ViewModel for the SEM_NOVA app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for StorageViewModel
        initializer {
            StorageViewModel(
                inventoryApplication().container.dataRepository
            )
        }
        // Initializer for OrderViewModel
        initializer<OrderViewModel> {
            OrderViewModel(
                inventoryApplication().container.dataRepository
            )
        }
        // Initializer for NewOrderViewModel
        initializer<NewOrderViewModel> {
            NewOrderViewModel(
                inventoryApplication().container.dataRepository
            )
        }
        // Initializer for NewOrderViewModel
        initializer<OrderDetailViewModel> {
            OrderDetailViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.dataRepository
            )
        }
        // Initializer for NewOrderViewModel
        initializer<ItemDetailViewModel> {
            ItemDetailViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.dataRepository
            )
        }
    }
}

/**
 * Extension function to queries for [WareFlowApplication] object and returns an instance of
 * [WareFlowApplication].
 */
fun CreationExtras.inventoryApplication(): WareFlowApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as WareFlowApplication)