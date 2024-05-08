package com.example.sem_nova.Data

import kotlinx.coroutines.flow.Flow

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
    override suspend fun updateOrder(order: Order) = orderDao.update(order)
}