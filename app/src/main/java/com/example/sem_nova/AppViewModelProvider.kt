package com.example.sem_nova

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sem_nova.GUI.OrderViewModel
import com.example.sem_nova.GUI.StorageViewModel

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
    }
}

/**
 * Extension function to queries for [WareFlowApplication] object and returns an instance of
 * [WareFlowApplication].
 */
fun CreationExtras.inventoryApplication(): WareFlowApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as WareFlowApplication)