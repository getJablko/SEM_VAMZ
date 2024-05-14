package com.example.sem_nova.GUI.ItemDetailScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_nova.AppViewModelProvider
import com.example.sem_nova.Data.Item
import com.example.sem_nova.GUI.NewOrderScreen.formatedPrice
import com.example.sem_nova.GUI.NewOrderScreen.toItem
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

/**
 * Unikátna cesta pre ItemDetailContent spolu s cestou ku konkrétnej položke na základe jej PK - mena
 */

object ItemDetailDestination : NavigationDestination {
    override val route = "item_details"
    const val itemName = "name"
    val routeWithArgs = "$route/{$itemName}"
}

/**
 * funkcia na zobrazenie UI komponentov pre ItemDetailContent
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */
@Composable
fun ItemDetailContent(
    viewModel: ItemDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val customFont = LocalCustomFont.current
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        // zavolanie metody na zobrazenie podrobnosti o polozke/iteme
        ItemDetailsBody(
            itemDetailsUiState = uiState.value,
            // lambda blok - kod, ktory sa vykona po predani polozky
            onSellItem = { viewModel.reduceQuantityByOne() },

            modifier = Modifier
                .fillMaxSize()
                .background(Color(241, 224, 254))
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState()),
            customFont = customFont
        )
    }
}

/**
 * funkcia na zobrazenie detailov o iteme/polozke
 * Upraveny kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */
@Composable
private fun ItemDetailsBody(
    itemDetailsUiState: ItemDetailsUiState,
    onSellItem: () -> Unit,
    modifier: Modifier = Modifier,
    customFont: FontFamily
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ItemDetails(
            item = itemDetailsUiState.itemDetails.toItem(), modifier = Modifier.fillMaxWidth()
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(
                    222,
                    77,
                    222
                )
            ),
            onClick = onSellItem,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, shape = RoundedCornerShape(30.dp)),

            enabled = !itemDetailsUiState.outOfStock
        ) {
            Text(
                stringResource(R.string.sell),
                fontFamily = customFont,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * funkcia na nacitanie informacii o iteme/polozke
 * Upraveny kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */
@Composable
fun ItemDetails(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(226, 207, 253, 255))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ItemDetailsRow(
                labelResID = R.string.item,
                itemDetail = item.name,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ItemDetailsRow(
                labelResID = R.string.quantity_in_stock,
                itemDetail = item.quantity.toString(),
                modifier = Modifier.padding(16.dp)
            )
            ItemDetailsRow(
                labelResID = R.string.price_per_item,
                itemDetail = item.formatedPrice(),
                modifier = Modifier.padding(16.dp)
            )
            ItemDetailsRow(
                labelResID = R.string.storage_place,
                itemDetail = item.place,
                modifier = Modifier.padding(16.dp)
            )
            ItemDetailsRow(
                labelResID = R.string.weigth_of_item,
                itemDetail = "${item.weight} ${stringResource(R.string.kg)}",
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}
/**
 * funkcia na zobrazenie(formatovanie) a naplnanie riadkov udajmi
 * Upraveny kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 */
@Composable
private fun ItemDetailsRow(
    @StringRes labelResID: Int, itemDetail: String, modifier: Modifier = Modifier
) {
    val customFont = LocalCustomFont.current
    Row(modifier = modifier) {
        Text(
            text = stringResource(labelResID),
            fontFamily = customFont,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = itemDetail,
            fontFamily = customFont,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}