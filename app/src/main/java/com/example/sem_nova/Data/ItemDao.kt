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

/**
 * Dao pre tabulku items
 */
@Dao
interface ItemDao {
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * from items WHERE name = :name")
    fun getItem(name: String): Flow<Item>

    @Query("UPDATE items SET quantity = :quantity, price = :price, place = :place, weight = :weight WHERE name = :name")
    suspend fun updateQuantity(
        name: String,
        quantity: Int,
        price: Double,
        place: String,
        weight: Double
    )

    /**
     * custom upsert funkcia pre aktualizovanie/vlozenie nedorucenych poloziek na sklad
     * napr. vytvorenie novej objednavky
     */
    @Transaction
    suspend fun upsert(item: Item) {
        val existingItem = getItem(item.name).firstOrNull()
        if (existingItem != null) {
            // uprav mnozstvo
            val newQuantity = existingItem.quantity //- 1
            updateQuantity(item.name, newQuantity, item.price, item.place, item.weight)
        } else {
            // ak neexistuje tak insert
            insertNew(item)
        }
    }

    /**
     * custom upsert funkcia pre aktualizovanie/vlozenie dorucenych poloziek na sklad
     * napr. dorucenie objednavky
     */
    @Transaction
    suspend fun upsert2(item: Item, newQuantity: Int) {
        val existingItem = getItem(item.name).firstOrNull()
        if (existingItem != null) {
            // uprav mnozstvo
            val newQuantity = existingItem.quantity + newQuantity
            updateQuantity(item.name, newQuantity, item.price, item.place, item.weight)
        } else {
            // ak neexistuje tak insert
            insertNew(item)
        }
    }

    /**
     * insert pre novy item
     */
    @Insert
    suspend fun insertNew(item: Item) {
        // nastavenie pociatocnej pocetnosti na 0 - tovar nie je doruceny
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