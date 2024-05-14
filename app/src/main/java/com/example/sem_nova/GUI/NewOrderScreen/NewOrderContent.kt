package com.example.sem_nova.GUI.NewOrderScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_nova.AppViewModelProvider
import com.example.sem_nova.GUI.ReceivedOrderScreen.HomeButton
import com.example.sem_nova.GUI.ReceivedOrderScreen.TextTitle
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

/**
 * Unikátna cesta pre NewOrderContent
 */

object NewOrderDestination : NavigationDestination {
    override val route = "new_order"
}

/**
 * funkcia na zobrazenie UI komponentov pre NewOrderContent
 */
@Composable
fun NewOrderContent(
    onHome: () -> Unit,
    // inicializácia viewmodelu
    viewModel: NewOrderViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val customFont = LocalCustomFont.current
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    // list medzier na základe orientácie zariadenia
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(35.dp, 25.dp) // portrait orientation
        } else {
            listOf(35.dp, 25.dp) // landscape orientation
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(spacerList[0]))
        // zobrazenie homeButtonu na základe orientácie
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            HomeButton(onHome = onHome)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextTitle(
                customFont = customFont,
                text = stringResource(id = R.string.newOrder)
            )
            Spacer(modifier = Modifier.height(spacerList[1]))

        }
    }
    /**
     * Prevzatá časť kódu z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
     */
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
    ) { innerPadding ->
        ItemEntryBody(
            itemUiState = viewModel.itemUiState,
            orderUiState1 = viewModel.orderUiState,
            onItemValueChange = viewModel::updateUiState,
            onOrderValueChange = viewModel::updateUiState1,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    viewModel.saveOrder()
                    onHome()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .background(Color(241, 224, 254))
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

/**
 * funkcia na zobrazenie formulára pri tvorbe objednávok + button na ich potvrdenie
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */
@Composable
fun ItemEntryBody(
    itemUiState: ItemUiState,
    orderUiState1: OrderUiState1,
    onItemValueChange: (ItemDetails) -> Unit,
    onOrderValueChange: (OrderDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val customFont = LocalCustomFont.current
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // formulár/textové polia potrebne pre vytvorenie novej objednavky
        ItemInputForm(
            itemDetails = itemUiState.itemDetails,
            orderDetails = orderUiState1.orderDetails,
            onValueChange = onItemValueChange,
            onOrderValueChange = onOrderValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        // button na vytvorenie objednavky
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(
                    222,
                    77,
                    222
                )
            ),
            onClick = onSaveClick,
            enabled = itemUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, shape = RoundedCornerShape(30.dp))
        ) {
            androidx.compose.material3.Text(
                fontFamily = customFont,
                fontSize = 20.sp,
                text = stringResource(R.string.receiveOrder)
            )
        }
    }
}

/**
 * funkcia, zobrazuje textove polia, ktore je potrebne vyplnit, zároven vytvara novy item a aj objednávku ktora ho bude obsahovat
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */
@Composable
fun ItemInputForm(
    itemDetails: ItemDetails,
    orderDetails: OrderDetails,
    modifier: Modifier = Modifier,
    onValueChange: (ItemDetails) -> Unit = {},
    onOrderValueChange: (OrderDetails) -> Unit = {},
    enabled: Boolean = true
) {
    val customFont = LocalCustomFont.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // textfield pre meno itemu
        OutlinedTextField(
            value = itemDetails.name,
            // vlozenie udajov do itemu a aj objednavky
            onValueChange = {
                onValueChange(itemDetails.copy(name = it))
                onOrderValueChange(orderDetails.copy(itemName = it))
            },
            textStyle = TextStyle(
                fontFamily = customFont, // Zmena fontu podľa stavu
            ),

            label = {
                androidx.compose.material3.Text(
                    text = stringResource(R.string.new_name),
                    style = TextStyle(
                        fontFamily = customFont,
                    )
                )
            },
            // zmena farieb
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(226, 207, 253),
                unfocusedContainerColor = Color(226, 207, 253),
                disabledContainerColor = Color(226, 207, 253),
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )
        // textfield pre cenu itemu
        OutlinedTextField(
            value = itemDetails.price,
            onValueChange = { onValueChange(itemDetails.copy(price = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = customFont, // Zmena fontu podľa stavu
            ),

            label = {
                androidx.compose.material3.Text(
                    text = stringResource(R.string.new_price),
                    style = TextStyle(
                        fontFamily = customFont,
                    )
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(226, 207, 253),
                unfocusedContainerColor = Color(226, 207, 253),
                disabledContainerColor = Color(226, 207, 253),
            ),
            leadingIcon = { androidx.compose.material3.Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // textfield pre počet ks. itemu
        OutlinedTextField(
            value = itemDetails.quantity,
            onValueChange = {
                onValueChange(itemDetails.copy(quantity = it))
                onOrderValueChange(orderDetails.copy(itemQuantity = it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(
                fontFamily = customFont, // Zmena fontu podľa stavu
            ),

            label = {
                androidx.compose.material3.Text(
                    text = stringResource(R.string.new_quantity),
                    style = TextStyle(
                        fontFamily = customFont,
                    )
                )
            }, colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(226, 207, 253),
                unfocusedContainerColor = Color(226, 207, 253),
                disabledContainerColor = Color(226, 207, 253),
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // textfield pre miesto dorucenia/uskladnenia itemu
        OutlinedTextField(
            value = itemDetails.place,
            onValueChange = { onValueChange(itemDetails.copy(place = it)) },
            textStyle = TextStyle(
                fontFamily = customFont, // Zmena fontu podľa stavu
            ),

            label = {
                androidx.compose.material3.Text(
                    text = stringResource(R.string.new_place),
                    style = TextStyle(
                        fontFamily = customFont,
                    )
                )
            }, colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(226, 207, 253),
                unfocusedContainerColor = Color(226, 207, 253),
                disabledContainerColor = Color(226, 207, 253),
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // textfield pre váhu 1 ks. itemu
        OutlinedTextField(
            value = itemDetails.weight,
            onValueChange = { onValueChange(itemDetails.copy(weight = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = customFont, // Zmena fontu podľa stavu
            ),

            label = {
                androidx.compose.material3.Text(
                    text = stringResource(R.string.new_weigth),
                    style = TextStyle(
                        fontFamily = customFont,
                    )
                )
            }, colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(226, 207, 253),
                unfocusedContainerColor = Color(226, 207, 253),
                disabledContainerColor = Color(226, 207, 253),
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // zobrazenie textu o povinnych poliach
        if (enabled) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
