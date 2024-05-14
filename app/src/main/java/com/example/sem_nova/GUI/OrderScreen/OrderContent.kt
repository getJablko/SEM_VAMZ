package com.example.sem_nova.GUI.OrderScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_nova.AppViewModelProvider
import com.example.sem_nova.Data.Order
import com.example.sem_nova.GUI.ReceivedOrderScreen.HomeButton
import com.example.sem_nova.GUI.ReceivedOrderScreen.TextTitle
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

object OrderDestination : NavigationDestination {
    override val route = "order"
}

@Composable
fun OrderContent(
    onHome: () -> Unit,
    onNewOrder: () -> Unit,
    onCurrentOrder: (Int) -> Unit,
    viewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val customFont = LocalCustomFont.current
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(20.dp, 35.dp) // portrait orientation
        } else {
            listOf(20.dp, 35.dp) // landscape orientation
        }
    }
    val orderUiState by viewModel.orderUiState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(spacerList[1]))
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            HomeButton(onHome = onHome)
        }

        Column(
            modifier = Modifier
                .weight(1f) // This makes this column take up all available space after the button
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextTitle(
                customFont = customFont,
                text = stringResource(id = R.string.newOrder)
            )
            Spacer(modifier = Modifier.height(spacerList[0]))

            Scaffold(

                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { onNewOrder() },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .padding(
                                end = WindowInsets.safeDrawing.asPaddingValues()
                                    .calculateEndPadding(LocalLayoutDirection.current)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.item_entry_title)
                        )
                    }
                },
            ) { innerPadding ->
                OrderBody(
                    orderList = orderUiState.orderList,
                    onOrderClick = onCurrentOrder,
                    contentPadding = innerPadding
                )
            }
        }
    }
}


@Composable
fun OrderBody(
    orderList: List<Order>,
    onOrderClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color(226, 207, 253, 255)),
        ) {
        if (orderList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_order_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            OrderList(
                orderList = orderList,
                onOrderClick = { onOrderClick(it.orderId) },
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
fun OrderList(
    orderList: List<Order>,
    onOrderClick: (Order) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(226, 207, 253, 255)),
        contentPadding = contentPadding
    ) {
        items(items = orderList, key = { it.orderId }) { order ->
            InventoryOrder(
                order = order,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onOrderClick(order) }
            )
        }
    }
}


@Composable
fun InventoryOrder(
    order: Order,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
                    text = stringResource(R.string.order_ID, order.orderId),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Text(
                text = stringResource(R.string.number_of_items, order.itemQuantity),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}



