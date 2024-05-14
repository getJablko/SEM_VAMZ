package com.example.sem_nova.GUI.ReceivedOrderScreen

import android.content.Intent
import android.content.res.Configuration
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_nova.AppViewModelProvider
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

/**
 * Unikátna cesta pre ReceiveOrderContent
 */

object ReceiveOrderDestination : NavigationDestination {
    override val route = "receive_order"
}

/**
 * funkcia na zobrazenie UI pre ReceiveOrderContent
 */
@Composable
fun ReceiveOrderContent(
    onHome: () -> Unit,
    // inicializácia viewmodelu
    viewModel: ReceiveOrderViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val context = LocalContext.current
    val customFont = LocalCustomFont.current
    val focusRequester = remember { FocusRequester() }
    var isTextFieldFocused by remember { mutableStateOf(false) }
    // zachovanie textu aj po zmene orientácie
    var RecivedOrderNumberText by rememberSaveable {
        mutableStateOf(context.getString(R.string.receivedOrderNumberOrder))
    }
    // premenna na spracovanie vysledkov z kamery
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { /* spracovanie vysledku */ }
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    // list medzier na základe orientácie
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(35.dp, 20.dp, 40.dp, 20.dp, 10.dp) // portrait orientation
        } else {
            listOf(35.dp, 10.dp, 40.dp, 20.dp, 10.dp) // landscape orientation
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(spacerList[0]))
        // zobrazenie homeButtonu na základe orientácie zariadenia - lepsie rozlozenie
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            HomeButton(onHome = onHome)
        }
        Column(
            modifier = Modifier
                .weight(1f) // zabratie vsetkeho miesta pod homeButtnom
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(spacerList[1]))

            // nadpis na základe zadaného parametra
            TextTitle(
                customFont = customFont,
                text = stringResource(id = R.string.receiveOrderText)
            )

            Spacer(modifier = Modifier.height(spacerList[2]))

            // textfield pre zadavanie cisla objednavky
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
                    // logika zobrazovania textu v textovom poli
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

            // tlacidlo na potvrdenie objednávky
            ReceivedOrderButton(
                customFont = customFont,
                RecivedOrderNumberText = RecivedOrderNumberText,
                viewModel = viewModel,
                onHome = onHome,
            )
            Spacer(modifier = Modifier.height(spacerList[4]))
            // button na zapnutie foťáku
            ScanButton(
                cameraLauncher = cameraLauncher
            )
        }
    }
}

/**
 * funkcia na zobrazenie tlacidla pre navrat do Menu
 * využíva sa vo viacerých obrazovkách
 */

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

/**
 * funkcia na zobrazenie nadpisov na zaklade zadaného parametra
 * využíva sa vo viacerých obrazovkách
 */

@Composable
fun TextTitle(
    customFont: FontFamily,
    text: String
) {
    Box(
        modifier = Modifier.shadow(75.dp)
    ) {
        Text(
            text = text,
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

/**
 * funkcia na zobrazenie tlačidla a spracovania objednávky
 * logika označovania objednávok za dorucene
 */
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
            contentColor = Color(222, 77, 222)
        ),
        onClick = {
            try {
                val orderId = RecivedOrderNumberText.toInt()
                viewModel.updateOrderId(orderId)
                viewModel.isOrderValid(orderId) { isValid ->
                    if (isValid == false) {
                        // zafarbenie tlačidla a chybový výpis
                        buttonColor = Color.Red
                        Toast.makeText(context, R.string.Invalid_order_number, Toast.LENGTH_SHORT).show()
                    } else {
                        // označenie objednávky za dorucenu a aktualizovanie skladu
                        buttonColor = Color.White
                        viewModel.markOrderAsDeliveredAndUpdateStorage(orderId)
                        onHome()
                    }
                }
            } catch (e: NumberFormatException) {
                // ak zadný string nie je cislo - osetrenie výnimky
                buttonColor = Color.Red
                Toast.makeText(context, R.string.Invalid_order_number, Toast.LENGTH_SHORT).show()
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
            fontFamily = customFont,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}


/**
 * funkcia na zobrazenie tlačidla s obrázkom na otvorenie fotoaplikácie
 */
@Composable
fun ScanButton(
    // parameter slúži na spustenie aktivity fotoaparátu
    cameraLauncher: ActivityResultLauncher<Intent>
) {
    IconButton(
        onClick = {
            // vytvorenie akcie
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // otvorenie fotoaplikacie zaznamenanie obrazku
            cameraLauncher.launch(takePictureIntent)
        },
        modifier = Modifier
            .size(190.dp)
    ) {
        // obrázok buttonu
        Image(
            painter = painterResource(R.drawable.scan),
            contentDescription = (stringResource(R.string.icon)),
            modifier = Modifier.size(140.dp)
        )
    }
}
