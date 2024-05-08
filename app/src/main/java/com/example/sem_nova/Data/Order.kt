package com.example.sem_nova.Data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "orders", foreignKeys = [ForeignKey(entity = Item::class, parentColumns = ["itemId"], childColumns = ["itemId"])])
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val itemId: Int,
    val itemQuantity: Int
    )