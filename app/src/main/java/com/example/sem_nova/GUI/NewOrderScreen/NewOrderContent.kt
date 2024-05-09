package com.example.sem_nova.GUI.NewOrderScreen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_nova.AppViewModelProvider
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale


object NewOrderDestination : NavigationDestination {
    override val route = "new_order"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderContent(
    onHome: () -> Unit,
    viewModel: NewOrderViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val customFont = LocalCustomFont.current
    //val orderUiState by viewModel.orderUiState.collectAsState()
    //val itemUiState by viewModel.itemUiState.collectAsState()
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(35.dp, 25.dp) // Heights for portrait orientation
        } else {
            listOf(35.dp, 25.dp) // Heights for landscape orientation
        }
    }
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf(context.getString(R.string.new_name)) }
    var price by rememberSaveable { mutableStateOf(context.getString(R.string.new_price)) }
    var quantity by rememberSaveable { mutableStateOf(context.getString(R.string.new_quantity)) }
    var place by rememberSaveable { mutableStateOf(context.getString(R.string.new_place)) }
    var weigth by rememberSaveable { mutableStateOf(context.getString(R.string.new_weigth)) }
    val focusRequester = remember { FocusRequester() }
    var isTextFieldFocused by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(spacerList[0]))
        HomeButton3(onHome = onHome)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text3(
                customFont = customFont
            )
            Spacer(modifier = Modifier.height(spacerList[1]))

            /*
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                value = name,
                onValueChange = { newText ->
                    name = newText
                },
                placeholder = {
                    androidx.compose.material3.Text(stringResource(id = R.string._hint))
                },
                textStyle = TextStyle(
                    fontFamily = customFont,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(30.dp, 15.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                        if ((focusState.isFocused && name == context.getString(R.string.new_name))) {
                            name = context.getString(R.string._hint)
                        }
                    },
                shape = RoundedCornerShape(30.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )

            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                value = price,
                onValueChange = { newText ->
                    price = newText
                },
                placeholder = {
                    androidx.compose.material3.Text(stringResource(id = R.string._hint))
                },
                textStyle = TextStyle(
                    fontFamily = customFont,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(30.dp, 15.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                        if ((focusState.isFocused && price == context.getString(R.string.new_price))) {
                            price = context.getString(R.string._hint)
                        }
                    },
                shape = RoundedCornerShape(30.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )

            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                value = quantity,
                onValueChange = { newText ->
                    quantity = newText
                },
                placeholder = {
                    androidx.compose.material3.Text(stringResource(id = R.string._hint))
                },
                textStyle = TextStyle(
                    fontFamily = customFont,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(30.dp, 15.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                        if ((focusState.isFocused && quantity == context.getString(R.string.new_quantity))) {
                            quantity = context.getString(R.string._hint)
                        }
                    },
                shape = RoundedCornerShape(30.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                value = place,
                onValueChange = { newText ->
                    place = newText
                },
                placeholder = {
                    androidx.compose.material3.Text(stringResource(id = R.string._hint))
                },
                textStyle = TextStyle(
                    fontFamily = customFont,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(30.dp, 15.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                        if ((focusState.isFocused && place == context.getString(R.string.new_place))) {
                            place = context.getString(R.string._hint)
                        }
                    },
                shape = RoundedCornerShape(30.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                value = weigth,
                onValueChange = { newText ->
                    weigth = newText
                },
                placeholder = {
                    androidx.compose.material3.Text(stringResource(id = R.string._hint))
                },
                textStyle = TextStyle(
                    fontFamily = customFont,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(30.dp, 15.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                        if ((focusState.isFocused && weigth == context.getString(R.string.new_weigth))) {
                            weigth = context.getString(R.string._hint)
                        }
                    },
                shape = RoundedCornerShape(30.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
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
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .shadow(6.dp, shape = RoundedCornerShape(30.dp))
            ) {
                androidx.compose.material3.Text(
                    text = context.getString(R.string.receiveOrder),
                    fontFamily = customFont,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

             */

        }
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
    ) { innerPadding ->
        ItemEntryBody(
            itemUiState = viewModel.itemUiState,
            orderUiState1 = viewModel.orderUiState,
            onItemValueChange = viewModel::updateUiState,
            onOrderValueChange = viewModel::updateUiState1,
            onSaveClick = {
                // Note: If the user rotates the screen very fast, the operation may get cancelled
                // and the item may not be saved in the Database. This is because when config
                // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                // be cancelled - since the scope is bound to composition.
                coroutineScope.launch {
                    viewModel.saveItem()
                    viewModel.saveOrder()
                    onHome()
                }
            },
            modifier = Modifier
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


@Composable
fun ItemEntryBody(
    itemUiState: ItemUiState,
    orderUiState1: OrderUiState1,
    onItemValueChange: (ItemDetails) -> Unit,
    onOrderValueChange: (OrderDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ItemInputForm(
            itemDetails = itemUiState.itemDetails,
            orderDetails = orderUiState1.orderDetails,
            onValueChange = onItemValueChange,
            onOrderValueChange = onOrderValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = itemUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.Text(text = stringResource(R.string.receiveOrder))
        }
    }
}

@Composable
fun ItemInputForm(
    itemDetails: ItemDetails,
    orderDetails: OrderDetails,
    modifier: Modifier = Modifier,
    onValueChange: (ItemDetails) -> Unit = {},
    onOrderValueChange: (OrderDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = itemDetails.name,
            onValueChange = {
                onValueChange(itemDetails.copy(name = it))
                onOrderValueChange(orderDetails.copy(itemName = it))
            },

            label = { androidx.compose.material3.Text(stringResource(R.string.new_name)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.price,
            onValueChange = { onValueChange(itemDetails.copy(price = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { androidx.compose.material3.Text(stringResource(R.string.new_price)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { androidx.compose.material3.Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.quantity,
            onValueChange = {
                onValueChange(itemDetails.copy(quantity = it))
                onOrderValueChange(orderDetails.copy(itemQuantity = it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { androidx.compose.material3.Text(stringResource(R.string.new_quantity)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.place,
            onValueChange = { onValueChange(itemDetails.copy(place = it)) },
            label = { androidx.compose.material3.Text(stringResource(R.string.new_place)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.weight,
            onValueChange = { onValueChange(itemDetails.copy(weight = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { androidx.compose.material3.Text(stringResource(R.string.new_weigth)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun HomeButton3(
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
fun Text3(
    customFont: FontFamily
) {
    Box(
        modifier = Modifier.shadow(75.dp)
    ) {
        androidx.compose.material3.Text(
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