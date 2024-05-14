package com.example.sem_nova.GUI.NewOrderScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.Data.Item
import com.example.sem_nova.Data.Order
import java.text.NumberFormat

/**
 * vievmodel pre tvorbu novej objednávky
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */

class NewOrderViewModel(private val dataRepository: DataRepository) : ViewModel() {


    /**
     * aktualne UI state itemu a objednavky
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set
    var orderUiState by mutableStateOf(OrderUiState1())
        private set

    /**
     * aktualizacia stavov itemov a objednavok
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    fun updateUiState1(orderDetails: OrderDetails) {
        orderUiState =
            OrderUiState1(orderDetails = orderDetails, isEntryValid = validateInput2(orderDetails))
    }

    /**
     * vlozenie itemov a objednavok do databázy + ich validácia (povinne polia/udaje)
     */
    suspend fun saveItem() {
        if (validateInput()) {
            dataRepository.upsertItem(itemUiState.itemDetails.toItem())
        }
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank() && place.isNotBlank() && weight.isNotBlank()
        }
    }

    suspend fun saveOrder() {
        if (validateInput2()) {
            dataRepository.insertOrder(orderUiState.orderDetails.toOrder())
        }
    }

    private fun validateInput2(uiState: OrderDetails = orderUiState.orderDetails): Boolean {
        return with(uiState) {
            itemName.isNotBlank() && itemQuantity.isNotBlank()
        }
    }

}

/**
 * Ui State pre Item a Order.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class OrderUiState1(
    val orderDetails: OrderDetails = OrderDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val place: String = "",
    val weight: String = ""
)

data class OrderDetails(
    val orderId: Int = 0,
    val itemName: String = "",
    val itemQuantity: String = "",
    val arrived: String = ""
)

/**
 * funkcia na konvertovanie [ItemDetails] na [Item] a  [OrderDetails] na [Order]
 */
fun ItemDetails.toItem(): Item = Item(
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0,
    place = place,
    weight = weight.toDoubleOrNull() ?: 0.0
)

fun OrderDetails.toOrder(): Order = Order(
    orderId = orderId,
    itemName = itemName,
    itemQuantity = itemQuantity.toIntOrNull() ?: 0,
    arrived = false,
)

/**
 * formatovanie ceny na základe meny
 */
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}


/**
 * funkcia na konvertovanie [Item] na [ItemDetails] a [Order] na [OrderDetails]
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    name = name,
    price = price.toString(),
    quantity = quantity.toString(),
    place = place,
    weight = weight.toString()
)

fun Order.toOrderDetails(): OrderDetails = OrderDetails(
    orderId = orderId,
    itemName = itemName,
    itemQuantity = itemQuantity.toString(),
    arrived = arrived.toString(),
)