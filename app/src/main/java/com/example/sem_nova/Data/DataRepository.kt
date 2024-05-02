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
    fun getItemStream(id: Int): Flow<Item?>
    fun getOrderStream(id: Int): Flow<Order?>
    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Item)

    suspend fun insertOrder(order: Order)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Item)
    suspend fun deleteOrder(order: Order)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Item)
    suspend fun updateOrder(order: Order)
}