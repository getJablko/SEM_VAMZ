package com.example.sem_nova.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * entita/tabulka items s atribútmi
 */

@Entity(tableName = "items")
data class Item(
    @PrimaryKey
    val name: String,
    val price: Double,
    val quantity: Int,
    val place: String,
    val weight: Double
)

