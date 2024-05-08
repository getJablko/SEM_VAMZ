package com.example.sem_nova.GUI

import android.content.pm.ActivityInfo
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
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
import com.example.sem_nova.Data.Order
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

object OrderDestination : NavigationDestination {
    override val route = "order"
}

@Composable
fun OrderContent(
    onHome: () -> Unit,
    viewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val customFont = LocalCustomFont.current
    val focusRequester = remember { FocusRequester() }
    var isTextFieldFocused by remember { mutableStateOf(false) }
    var productName by remember {
        mutableStateOf(context.getString(R.string.productName))
    }
    var productQuantity by remember {
        mutableStateOf(context.getString(R.string.productQuantity))
    }
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(20.dp, 35.dp) // Heights for portrait orientation
        } else {
            listOf(20.dp, 35.dp) // Heights for landscape orientation
        }
    }
    val orderUiState by viewModel.orderUiState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(spacerList[1]))
        HomeButton1(onHome = onHome)

        Column(
            modifier = Modifier
                .weight(1f) // This makes this column take up all available space after the button
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text1(
                customFont = customFont
            )
            Spacer(modifier = Modifier.height(spacerList[0]))
            /*
                TextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    value = productName,
                    onValueChange = { newText ->
                        productName = newText
                    },
                    placeholder = {
                        Text(stringResource(id = R.string._hint))
                    },
                    textStyle = TextStyle(
                        fontFamily = customFont,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .padding(30.dp, 15.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                        .onFocusChanged { focusState ->
                            isTextFieldFocused = focusState.isFocused
                            if (focusState.isFocused && !productName.isEmpty() && productName == context.getString(
                                    R.string.productName
                                )
                            ) {
                                productName = context.getString(R.string._hint)
                            } else if (focusState.isFocused && !productName.isEmpty() && productName != context.getString(
                                    R.string.productName
                                )
                            ) {
                                // do nothing
                            } else if (!focusState.isFocused && productName.isEmpty()) {
                                productName =
                                    context.getString(R.string.productName)
                            }
                        },
                    shape = RoundedCornerShape(30.dp)
                )

                TextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    value = productQuantity,
                    onValueChange = { newText ->
                        productQuantity = newText
                    },
                    placeholder = {
                        Text(stringResource(id = R.string._hint))
                    },
                    textStyle = TextStyle(
                        fontFamily = customFont,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .padding(30.dp, 15.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                        .onFocusChanged { focusState ->
                            isTextFieldFocused = focusState.isFocused
                            if (focusState.isFocused && !productQuantity.isEmpty() && productQuantity == context.getString(
                                    R.string.productQuantity
                                )
                            ) {
                                productQuantity = context.getString(R.string._hint)
                            } else if (focusState.isFocused && !productQuantity.isEmpty() && productQuantity != context.getString(
                                    R.string.productQuantity
                                )
                            ) {
                                // do nothing
                            } else if (!focusState.isFocused && productQuantity.isEmpty()) {
                                productQuantity =
                                    context.getString(R.string.productQuantity)
                            }
                        },
                    shape = RoundedCornerShape(30.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
                FinalPriceText(
                    customFont = customFont
                )

                Spacer(modifier = Modifier.height(20.dp))

                CreateOrderButton(
                    customFont = customFont
                )

         */

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {/* TODO*/ },
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
                    onItemClick = { /* TODO */ },
                    modifier = Modifier.padding(8.dp),
                    contentPadding = innerPadding
                )
            }
        }
    }
}


@Composable
fun OrderBody(
    orderList: List<Order>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),

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
                onItemClick = { onItemClick(it.orderId) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun OrderList(
    orderList: List<Order>,
    onItemClick: (Order) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = orderList, key = { it.orderId }) { order ->
            InventoryOrder(
                order = order,
                //onItemClick = onItemClick,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(order) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryOrder(
    order: Order,
    //onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        //colors = CardDefaults.cardColors(containerColor = Color(236, 207, 253))
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
                    text = order.orderId.toString(),
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

@Composable
fun HomeButton1(
    onHome: () -> Unit
) {
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
fun Text1(
    customFont: FontFamily
) {
    Box(
        modifier = Modifier.shadow(75.dp)
    ) {
        Text(
            text = stringResource(id = R.string.newOrder),
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

@Composable
fun FinalPriceText(
    customFont: FontFamily
) {
    Box(
        modifier = Modifier.shadow(75.dp)
    ) {
        Text(
            text = stringResource(id = R.string.price),
            fontFamily = customFont,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CreateOrderButton(
    customFont: FontFamily
) {
    val context = LocalContext.current
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(222, 77, 222)
        ),
        onClick = { /* Handle login button click */ },
        modifier = Modifier
            .padding(75.dp, 15.dp)
            .fillMaxWidth()
            .shadow(10.dp, shape = RoundedCornerShape(30.dp))
            .height(45.dp)
    ) {
        Text(
            text = context.getString(R.string.createOrder),
            fontFamily = customFont,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}