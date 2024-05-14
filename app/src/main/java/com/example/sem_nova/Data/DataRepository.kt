package com.example.sem_nova.Data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface DataRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Item>>
    fun getAllOrdersStream(): Flow<List<Order>>
    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(name: String): Flow<Item?>
    fun getOrderStream(id: Int): Flow<Order?>
    /**
     * Insert item in the data source
     */
    suspend fun upsertItem(item: Item)

    suspend fun upsertItem2(item: Item, newQuantity: Int)
    suspend fun insertOrder(order: Order)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Item)
    suspend fun deleteOrder(order: Order)

    /**
     * Update item in the data source
     */
    suspend fun updateOrder(order: Order)

    /**
     * Update the storage data after marking an order as delivered.
     * This method should be called after an order is marked as delivered to ensure the storage screen is updated.
     */
    suspend fun updateStorage(deliveredOrder: Order)

}