package com.example.sem_nova.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

@Dao
interface ItemDao {
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * from items WHERE name = :name")
    fun getItem(name: String): Flow<Item>

    @Query("UPDATE items SET quantity = :quantity, price = :price, place = :place, weight = :weight WHERE name = :name")
    suspend fun updateQuantity(name: String, quantity: Int, price: Double, place: String, weight: Double)

    @Transaction
    suspend fun upsert(item: Item) {
        val existingItem = getItem(item.name).firstOrNull()
        if (existingItem != null) {
            // Item exists, update its quantity
            val newQuantity = existingItem.quantity //- 1
            updateQuantity(item.name, newQuantity,item.price,item.place,item.weight)
        } else {
            // Item doesn't exist, insert it
            insertNew(item)
        }
    }

    @Transaction
    suspend fun upsert2(item: Item, newQuantity: Int) {
        val existingItem = getItem(item.name).firstOrNull()
        if (existingItem != null) {
            // Item exists, update its quantity
            val newQuantity = existingItem.quantity + newQuantity
            updateQuantity(item.name, newQuantity,item.price,item.place,item.weight)
        } else {
            // Item doesn't exist, insert it
            insertNew(item)
        }
    }

    @Insert
    suspend fun insertNew(item: Item) {
        // Set quantity to 0 before insertion
        val itemWithDefaultQuantity = item.copy(quantity = 0)
        insertInternal(itemWithDefaultQuantity)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInternal(item: Item)


    @Update
    suspend fun update(item: Item)
    @Insert
    suspend fun insert(item: Item)
    @Delete
    suspend fun delete(item: Item)
}