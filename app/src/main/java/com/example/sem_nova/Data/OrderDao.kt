package com.example.sem_nova.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * from orders ORDER BY orderId ASC")
    fun getAllItems(): Flow<List<Order>>

    @Query("SELECT * from orders WHERE OrderId = :id")
    fun getItem(id: Int): Flow<Order>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Order)

    @Update
    suspend fun update(item: Order)

    @Delete
    suspend fun delete(item: Order)
}