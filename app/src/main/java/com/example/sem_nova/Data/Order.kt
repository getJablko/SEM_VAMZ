package com.example.sem_nova.Data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "orders", foreignKeys = [ForeignKey(entity = Item::class, parentColumns = ["name"], childColumns = ["itemName"])])
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val itemName: String,
    val itemQuantity: Int,
    val arrived: Boolean
    )