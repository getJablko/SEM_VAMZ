package com.example.sem_nova.GUI

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_nova.GUI.ReceivedOrderScreen.TextTitle
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

/**
 * Unikátna cesta pre MainContent
 */

object MainDestination : NavigationDestination {
    override val route = "main"
}

/**
 * Funkcia na zobrazenie UI komponentov v menu aplikácie
 */

@Composable
fun MainContent(
    onLogout: () -> Unit,
    onReceiveOrder: () -> Unit,
    onNewOrder: () -> Unit,
    onStorage: () -> Unit
) {
    val customFont = LocalCustomFont.current
    val configuration = LocalConfiguration.current
    // práca so senzorom - orientáciou mobilu
    val orientation = configuration.orientation
    // definovanie medzier medzi UI prvkami na základe orientácie mobilu
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(120.dp, 10.dp) // portrait orientation
        } else {
            listOf(50.dp, 10.dp) // landscape orientation
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // zobrazenie rozloženia pre "portrait" režim aplikácie
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            Spacer(modifier = Modifier.height(spacerList[0]))

            TextTitle(
                customFont = customFont,
                text = stringResource(id = R.string.welcomeBack_hint)
            )

            Spacer(modifier = Modifier.height(spacerList[1]))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonRecieveOrder(
                    onReceiveOrder = onReceiveOrder
                )
                ButtonNewOrder(
                    onNewOrder = onNewOrder
                )
            }
            ButtonStorage(
                onStorage = onStorage
            )
            LogoutButton(
                customFont = customFont,
                onLogout = onLogout
            )
            // zobrazenie rozloženia pre "landscape" režim aplikácie
        } else {
            Spacer(modifier = Modifier.height(spacerList[0]))
            TextTitle(
                customFont = customFont,
                text = stringResource(id = R.string.welcomeBack_hint)
            )
            Spacer(modifier = Modifier.height(spacerList[1]))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonRecieveOrder(
                    onReceiveOrder = onReceiveOrder
                )
                ButtonNewOrder(
                    onNewOrder = onNewOrder
                )
                ButtonStorage(
                    onStorage = onStorage
                )
            }
            LogoutButton(
                customFont = customFont,
                onLogout = onLogout
            )
        }
    }
}

/**
 * funkcia tlačidla na prechod do sekcie ReceiveOrderContent
 */
@Composable
fun ButtonRecieveOrder(
    onReceiveOrder: () -> Unit
) {
    IconButton(
        onClick = { onReceiveOrder() },
        modifier = Modifier
            .size(180.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.reciveorder),
            contentDescription = (stringResource(R.string.icon)),
            modifier = Modifier.size(140.dp)
        )
    }
}

/**
 * funkcia tlačidla na prechod do sekcie OrderContent
 */
@Composable
fun ButtonNewOrder(
    onNewOrder: () -> Unit
) {
    IconButton(
        onClick = { onNewOrder() },
        modifier = Modifier
            .size(180.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.neworder),
            contentDescription = (stringResource(R.string.icon)),
            modifier = Modifier.size(140.dp)
        )
    }
}

/**
 * funkcia tlačidla na prechod do sekcie StorageContent
 */
@Composable
fun ButtonStorage(
    onStorage: () -> Unit
) {
    IconButton(
        onClick = { onStorage() },
        modifier = Modifier
            .size(180.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.storage),
            contentDescription = (stringResource(R.string.icon)),
            modifier = Modifier.size(140.dp)
        )
    }
}

/**
 * funkcia tlačidla na odhlásenie sa z aplikácie
 */
@Composable
fun LogoutButton(
    customFont: FontFamily,
    onLogout: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(222, 77, 222)
        ),
        onClick = { onLogout() },
        modifier = Modifier
            .padding(75.dp, 15.dp)
            .fillMaxWidth()
            .shadow(10.dp, shape = RoundedCornerShape(30.dp))
            .height(45.dp)
    ) {
        Text(
            text = stringResource(R.string.Logout),
            fontFamily = customFont,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

