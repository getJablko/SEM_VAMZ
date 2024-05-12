package com.example.sem_nova.Data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class OfflineRepository(
    private val itemDao: ItemDao,
    private val orderDao: OrderDao
) : DataRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()
    override fun getAllOrdersStream(): Flow<List<Order>> = orderDao.getAllItems()

    override fun getItemStream(name: String): Flow<Item?> = itemDao.getItem(name)
    override fun getOrderStream(id: Int): Flow<Order?> = orderDao.getItem(id)


    override suspend fun insertOrder(order: Order) = orderDao.insert(order)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)
    override suspend fun deleteOrder(order: Order) = orderDao.delete(order)

    override suspend fun upsertItem(item: Item) = itemDao.upsert(item)

    override suspend fun upsertItem2(item: Item) = itemDao.upsert2(item)
    override suspend fun updateOrder(order: Order) = orderDao.update(order)
    override suspend fun updateStorage(deliveredOrder: Order) {
        // Find the item in the storage
        val itemInStorage = getItemStream(deliveredOrder.itemName).firstOrNull()

        // If the item exists in storage
        if (itemInStorage != null) {
            // Update the quantity of the item
            val updatedQuantity = itemInStorage.quantity + deliveredOrder.itemQuantity
            val storedItem = itemInStorage.copy(quantity = updatedQuantity)
            upsertItem2(storedItem)
        } else {
            // Handle the case where the item is not found in the storage
            // This may involve adding the item to the storage or other actions
            // For simplicity, let's print a message indicating the item was not found
            println("Item '${deliveredOrder.itemName}' not found in storage.")
        }

    }


}