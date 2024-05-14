package com.example.sem_nova.Data

import android.content.Context

/**
 * App container for Dependency injection.
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */
interface AppContainer {
    val dataRepository: DataRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val dataRepository: DataRepository by lazy {
        OfflineRepository(InventoryDatabase.getDatabase(context).itemDao(), InventoryDatabase.getDatabase(context).orderDao())    }
}