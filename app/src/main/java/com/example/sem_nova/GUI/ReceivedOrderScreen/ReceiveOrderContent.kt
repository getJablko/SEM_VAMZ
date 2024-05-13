package com.example.sem_nova.GUI.ReceivedOrderScreen

import android.content.Intent
import android.content.res.Configuration
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_nova.AppViewModelProvider
import com.example.sem_nova.Data.Order
import com.example.sem_nova.GUI.OrderDetailScreen.OrderDetailViewModel
import com.example.sem_nova.Navigation.NavigationDestination
import kotlinx.coroutines.launch


object ReceiveOrderDestination : NavigationDestination {
    override val route = "receive_order"
}

@Composable
fun ReceiveOrderContent(
    onHome: () -> Unit,
    viewModel: ReceiveOrderViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val context = LocalContext.current
    val customFont = LocalCustomFont.current
    val focusRequester = remember { FocusRequester() }
    var isTextFieldFocused by remember { mutableStateOf(false) }
    var RecivedOrderNumberText by remember {
        mutableStateOf(context.getString(R.string.receivedOrderNumberOrder))
    }
    val uiState = viewModel.orderDetailsUiState.value
    val coroutineScope = rememberCoroutineScope()
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { /* handle result if needed */ }
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(35.dp, 20.dp, 40.dp, 20.dp, 10.dp) // Heights for portrait orientation
        } else {
            listOf(35.dp, 10.dp, 40.dp, 20.dp, 10.dp) // Heights for landscape orientation
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(spacerList[0]))
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            HomeButton(onHome = onHome)
        }
        Column(
            modifier = Modifier
                .weight(1f) // This makes this column take up all available space after the button
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(spacerList[1]))

            Text(customFont = customFont)

            Spacer(modifier = Modifier.height(spacerList[2]))

            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                value = RecivedOrderNumberText,
                onValueChange = { newText ->
                    RecivedOrderNumberText = newText
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
                        if (focusState.isFocused && !RecivedOrderNumberText.isEmpty() && RecivedOrderNumberText == context.getString(
                                R.string.receivedOrderNumberOrder
                            )
                        ) {
                            RecivedOrderNumberText = context.getString(R.string._hint)
                        } else if (focusState.isFocused && !RecivedOrderNumberText.isEmpty() && RecivedOrderNumberText != context.getString(
                                R.string.receivedOrderNumberOrder
                            )
                        ) {
                            // do nothing
                        } else if (!focusState.isFocused && RecivedOrderNumberText.isEmpty()) {
                            RecivedOrderNumberText =
                                context.getString(R.string.receivedOrderNumberOrder)
                        }
                    },
                shape = RoundedCornerShape(30.dp)
            )

            Spacer(modifier = Modifier.height(spacerList[3]))
            ReceivedOrderButton(
                customFont = customFont,
                RecivedOrderNumberText = RecivedOrderNumberText,
                viewModel = viewModel,
                onHome = onHome,
            )
            Spacer(modifier = Modifier.height(spacerList[4]))
            ScanButton(
                cameraLauncher = cameraLauncher
            )
        }
    }
}

@Composable
fun HomeButton(
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
fun Text(customFont: FontFamily) {
    Box(
        modifier = Modifier.shadow(75.dp)
    ) {
        Text(
            text = stringResource(id = R.string.receiveOrderText),
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
fun ReceivedOrderButton(
    customFont: FontFamily,
    RecivedOrderNumberText: String,
    viewModel: ReceiveOrderViewModel,
    onHome: () -> Unit,
) {
    val context = LocalContext.current
    var buttonColor by remember { mutableStateOf(Color.White) }
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color(222, 77, 222) // Farba obsahu v normálnom stave
        ),
        onClick = {
            viewModel.updateOrderId(RecivedOrderNumberText.toInt())
            viewModel.isOrderValid(RecivedOrderNumberText.toInt()) { isValid ->
                if (isValid == false) {
                   //TODO
                    buttonColor = Color.Red
                } else {
                    buttonColor = Color.White
                    viewModel.markOrderAsDeliveredAndUpdateStorage(RecivedOrderNumberText.toInt())
                    onHome()
                }
            }
        },
        modifier = Modifier
            .padding(46.dp, 15.dp)
            .fillMaxWidth()
            .shadow(10.dp, shape = RoundedCornerShape(30.dp))
            .height(45.dp)
    ) {
        Text(
            text = context.getString(R.string.receiveOrder),
            fontFamily = customFont, // vlastný font pre text
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ScanButton(
    cameraLauncher: ActivityResultLauncher<Intent>
) {
    IconButton(
        onClick = {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(takePictureIntent)
        },
        modifier = Modifier
            .size(190.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.scan),
            contentDescription = (stringResource(R.string.icon)),
            modifier = Modifier.size(140.dp)
        )
    }
}
