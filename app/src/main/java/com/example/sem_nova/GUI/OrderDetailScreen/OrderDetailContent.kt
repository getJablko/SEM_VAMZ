package com.example.sem_nova.GUI.OrderDetailScreen

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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.sem_nova.Data.Order
import com.example.sem_nova.GUI.NewOrderScreen.toOrder
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont
import kotlinx.coroutines.launch


object OrderDetailDestination : NavigationDestination {
    override val route = "order_detail"
    const val orderId = "orderId"
    val routeWithArgs = "$route/{$orderId}"
}

@Composable
fun OrderDetailContent(
    onHome: () -> Unit,
    viewModel: OrderDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val customFont = LocalCustomFont.current
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        OrderDetailsBody(
            orderDetailsUiState = uiState.value,
            onArrivedOrder = { order ->
                coroutineScope.launch {
                    viewModel.markOrderAsDeliveredAndUpdateStorage(order)
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
                .verticalScroll(rememberScrollState()),
            customFont = customFont
        )
    }
}


@Composable
private fun OrderDetailsBody(
    orderDetailsUiState: OrderDetailsUiState,
    onArrivedOrder: (Order) -> Unit,
    modifier: Modifier = Modifier,
    customFont: FontFamily
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OrderDetails(
            order = orderDetailsUiState.orderDetails.toOrder(), modifier = Modifier.fillMaxWidth()
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
            onClick = { onArrivedOrder(orderDetailsUiState.orderDetails.toOrder()) },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, shape = RoundedCornerShape(30.dp)),
            enabled = !orderDetailsUiState.arrived
        ) {
            Text(
                stringResource(R.string.mark_as_arrived),
                fontFamily = customFont,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun OrderDetails(
    order: Order, modifier: Modifier = Modifier
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
                labelResID = R.string.order_id,
                orderDetail = order.orderId.toString(),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ItemDetailsRow(
                labelResID = R.string.itemName,
                orderDetail = order.itemName,
                modifier = Modifier.padding(16.dp)
            )
            ItemDetailsRow(
                labelResID = R.string.ItemQuantity,
                orderDetail = order.itemQuantity.toString(),
                modifier = Modifier.padding(16.dp)
            )
            ItemDetailsRow(
                labelResID = R.string.arrived,
                orderDetail = order.arrived.toString(),
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

@Composable
private fun ItemDetailsRow(
    @StringRes labelResID: Int, orderDetail: String, modifier: Modifier = Modifier
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
            text = orderDetail,
            fontFamily = customFont,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}


