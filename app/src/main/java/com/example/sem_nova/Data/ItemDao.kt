package com.example.sem_nova.Data

import android.provider.SyncStateContract.Helpers.insert
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
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

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Transaction
    suspend fun upsert(item: Item) {
        val existingItem = getItem(item.name).firstOrNull()
        if (existingItem != null) {
            // Item exists, update its quantity
            val newQuantity = existingItem.quantity
            updateQuantity(item.name, newQuantity,item.price,item.place,item.weight)
        } else {
            // Item doesn't exist, insert it
            insert(item)
        }
    }

    @Transaction
    suspend fun upsert2(item: Item) {
        val existingItem = getItem(item.name).firstOrNull()
        if (existingItem != null) {
            // Item exists, update its quantity
            val newQuantity = existingItem.quantity + item.quantity
            updateQuantity(item.name, newQuantity,item.price,item.place,item.weight)
        } else {
            // Item doesn't exist, insert it
            insert(item)
        }
    }
    @Update
    suspend fun update(item: Item)
    @Insert
    suspend fun insert(item: Item)
    @Delete
    suspend fun delete(item: Item)
}