package com.example.sem_nova.Data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

/**
 * Trieda OfflineRepository slúži ako implementácia rozhrania DataRepository
 * zodpovedá za komunikáciu s databázou pomocou ItemDao a OrderDao
 */

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
    override suspend fun upsertItem3(item: Item) = itemDao.upsert3(item)
    override suspend fun upsertItem2(item: Item, newQuantity: Int) = itemDao.upsert2(item, newQuantity)

    override suspend fun updateOrder(order: Order) = orderDao.update(order)
    /**
     * funkcia na aktualizovanie počtu kusov poloziek/itemov na sklade
     */
    override suspend fun updateStorage(deliveredOrder: Order) {
        // najdenie itemu v sklade
        val itemInStorage = getItemStream(deliveredOrder.itemName).firstOrNull()

        // overenie existencie itemu
        if (itemInStorage != null) {
            // aktualizacia poctu na sklade
            val updatedQuantity =  deliveredOrder.itemQuantity
            upsertItem2(itemInStorage, updatedQuantity)
        }
    }


}