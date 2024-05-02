package com.example.sem_nova.Data

import kotlinx.coroutines.flow.Flow

class OfflineRepository(
    private val itemDao: ItemDao,
    private val orderDao: OrderDao
) : DataRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()
    override fun getAllOrdersStream(): Flow<List<Order>> = orderDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)
    override fun getOrderStream(id: Int): Flow<Order?> = orderDao.getItem(id)

    override suspend fun insertItem(item: Item) = itemDao.insert(item)
    override suspend fun insertOrder(order: Order) = orderDao.insert(order)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)
    override suspend fun deleteOrder(order: Order) = orderDao.delete(order)

    override suspend fun updateItem(item: Item) = itemDao.update(item)
    override suspend fun updateOrder(order: Order) = orderDao.update(order)
}