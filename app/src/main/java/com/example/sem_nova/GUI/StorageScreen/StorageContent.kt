package com.example.sem_nova.GUI.StorageScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_nova.AppViewModelProvider
import com.example.sem_nova.Data.Item
import com.example.sem_nova.GUI.ReceivedOrderScreen.HomeButton
import com.example.sem_nova.GUI.ReceivedOrderScreen.TextTitle
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont
import java.text.NumberFormat

/**
 * Unikátna cesta pre StorageContent
 */

object StorageDestination : NavigationDestination {
    override val route = "storage"
}

/**
 * funkcia na zobrazenie UI obrazovky skladu
 */

@Composable
fun StorageContent(
    navigateToItemUpdate: (String) -> Unit,
    onHome: () -> Unit,
    // vytvorenie viewmodelu pre túto obrazovku
    viewModel: StorageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val customFont = LocalCustomFont.current
    // načitanie storageUiState z viewmodelu - list itemov
    val storageUiState by viewModel.storageUiState.collectAsState()
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    // list medzie na základe orientácie zariadenia
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(35.dp, 25.dp) // portrait orientation
        } else {
            listOf(35.dp, 25.dp) // landscape orientation
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(spacerList[0]))
        // zobrazovanie homeButtonu na základe orientácie obrazovky - pre lepšie rozloženie
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            HomeButton(onHome = onHome)
        }
        Column(
            modifier = Modifier
                .weight(1f) // zabratie maximalneho možneho miesta pod buttonom
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // zobrazenie nadpisu na základe zadaného parametra
            TextTitle(
                customFont = customFont,
                text = stringResource(id = R.string.storage)
            )

            Spacer(modifier = Modifier.height(spacerList[1]))

            // zavolanie funkcie na zobrazenie položiek skladu
            StorageBody(
                itemList = storageUiState.itemList,
                onItemClick = navigateToItemUpdate,
            )

        }
    }
}

/**
 * funkcia na zobrazenie položiek skladu
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */

@Composable
fun StorageBody(
    itemList: List<Item>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color(226, 207, 253, 255)),
    ) {
        // ak je sklad prázdny
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
            // ak sa v sklade nachádzajú položky
        } else {
            InventoryList(
                itemList = itemList,
                onItemClick = { onItemClick(it.name) },
                contentPadding = contentPadding,
            )
        }
    }
}

/**
 * funkcia, ktorá načíta položky do lazyColumn pomocou funkcie InventoryItem
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */

@Composable
fun InventoryList(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(226, 207, 253, 255)),
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.name }) { item ->
            InventoryItem(
                item = item,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(item) }
            )
        }
    }
}

/**
 * funkcia, ktorá načíta položku do UI komponentu Card, kde sa zobrazujú informácie o tejto položke
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */

@Composable
fun InventoryItem(
    item: Item,
    modifier: Modifier = Modifier
) {
    val customFont = LocalCustomFont.current

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(241, 224, 254, 255))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = customFont,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = NumberFormat.getCurrencyInstance().format(item.price)
                        .toString() + stringResource(R.string.one_piece),
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = customFont,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = stringResource(R.string.in_stock, item.quantity),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = customFont,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
