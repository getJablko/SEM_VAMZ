package com.example.sem_nova.Data

import android.content.Context

/**
 * App container for Dependency injection.
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