package com.example.sem_nova.GUI

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_nova.AppViewModelProvider
import com.example.sem_nova.Data.Item
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

object StorageDestination : NavigationDestination {
    override val route = "storage"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageContent(
    onHome: () -> Unit,
    viewModel: StorageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val customFont = LocalCustomFont.current
    val homeUiState by viewModel.storageUiState.collectAsState()
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(80.dp, 15.dp,25.dp) // Heights for portrait orientation
        } else {
            listOf(30.dp, 15.dp, 25.dp) // Heights for landscape orientation
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeButton2(
            onHome = onHome,
            spacerList = spacerList
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(spacerList[0]))

        Text2(
            customFont = customFont
        )
        Spacer(modifier = Modifier.height(spacerList[2]))

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {/* TODO*/},
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .padding(
                            end = WindowInsets.safeDrawing.asPaddingValues()
                                .calculateEndPadding(LocalLayoutDirection.current)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.item_entry_title)
                    )
                }
            },
        ) {  innerPadding ->
            StorageBody(
                itemList = homeUiState.itemList,
                onItemClick = { /* TODO */ },
                modifier = Modifier.padding(8.dp),
                contentPadding = innerPadding
            )
        }
    }
}

@Composable
fun StorageBody(
    itemList: List<Item>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            InventoryList(
                itemList = itemList,
                onItemClick = { onItemClick(it.itemId) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun InventoryList(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.itemId }) { item ->
            InventoryItem(
                item = item,
                //onItemClick = onItemClick,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryItem(
    item: Item,
    //onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        //onClick = onItemClick
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
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = item.price.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = stringResource(R.string.in_stock, item.quantity),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun HomeButton2(
    onHome: () -> Unit,
    spacerList: List<Dp>
) {
    Spacer(modifier = Modifier.height(spacerList[1]))
    IconButton(
        onClick = { onHome() },
        modifier = Modifier
            .size(100.dp)
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = (stringResource(R.string.icon)),
            modifier = Modifier.size(55.dp)
        )
    }
}

@Composable
fun Text2(
    customFont: FontFamily
) {
    Box(
        modifier = Modifier.shadow(75.dp)
    ) {
        Text(
            text = stringResource(id = R.string.storage),
            fontFamily = customFont,
            color = Color.White,
            fontSize = 42.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                lineHeight = 42.sp,
            )
        )
    }
}
