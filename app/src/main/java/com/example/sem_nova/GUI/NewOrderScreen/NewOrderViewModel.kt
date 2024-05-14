package com.example.sem_nova.GUI.NewOrderScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sem_nova.Data.DataRepository
import com.example.sem_nova.Data.Item
import com.example.sem_nova.Data.Order
import java.text.NumberFormat

class NewOrderViewModel(private val dataRepository: DataRepository) : ViewModel() {


    /**
     * Holds current item and order ui state
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set
    var orderUiState by mutableStateOf(OrderUiState1())
        private set

    /**
     * Updates the [itemUiState] and [orderUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
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
     * Inserts an [Item] and [Order] in the Room database
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
 * Represents Ui State for an Item and Order.
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
 * Extension function to convert [ItemUiState] to [Item] and [OrderUiState1] to [Order]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemUiState] is not a valid [Int], then the quantity will be set to 0
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

fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}


/**
 * Extension function to convert [Item] to [ItemDetails] and [Order] to [OrderDetails]
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