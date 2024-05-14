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
import com.example.sem_nova.GUI.ReceivedOrderScreen.ReceiveOrderViewModel
import com.example.sem_nova.GUI.StorageScreen.StorageViewModel

/**
 * Provides Factory to create instance of ViewModel for the SEM_NOVA app
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            StorageViewModel(
                inventoryApplication().container.dataRepository
            )
        }

        initializer<OrderViewModel> {
            OrderViewModel(
                inventoryApplication().container.dataRepository
            )
        }

        initializer<NewOrderViewModel> {
            NewOrderViewModel(
                inventoryApplication().container.dataRepository
            )
        }

        initializer<OrderDetailViewModel> {
            OrderDetailViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.dataRepository
            )
        }

        initializer<ItemDetailViewModel> {
            ItemDetailViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.dataRepository
            )
        }

        initializer<ReceiveOrderViewModel> {
            ReceiveOrderViewModel(
                inventoryApplication().container.dataRepository
            )
        }
    }
}

/**
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 * Extension function to queries for [WareFlowApplication] object and returns an instance of
 * [WareFlowApplication].
 */
fun CreationExtras.inventoryApplication(): WareFlowApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as WareFlowApplication)