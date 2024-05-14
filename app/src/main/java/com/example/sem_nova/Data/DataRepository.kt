package com.example.sem_nova.Data

import kotlinx.coroutines.flow.Flow

/**
 * repozitar ktory poskytuje zakladne operacie s databázov pre Item a Order
 * Upraveny kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */
interface DataRepository {
    /**
     * ziskanie vsetkych itemov/objednavok
     */
    fun getAllItemsStream(): Flow<List<Item>>
    fun getAllOrdersStream(): Flow<List<Order>>
    /**
     * ziskanie jednotlivych itemov/objednavok na zaklade PK
     */
    fun getItemStream(name: String): Flow<Item?>
    fun getOrderStream(id: Int): Flow<Order?>
    /**
     * vkladanie/aktualizacia udajov
     */
    suspend fun upsertItem(item: Item)

    suspend fun upsertItem2(item: Item, newQuantity: Int)
    suspend fun insertOrder(order: Order)
    suspend fun updateOrder(order: Order)
    /**
     * mazanie udajov
     */
    suspend fun deleteItem(item: Item)
    suspend fun deleteOrder(order: Order)


    /**
     * aktualizacia skladu po doruceny objednavky
     */
    suspend fun updateStorage(deliveredOrder: Order)

}