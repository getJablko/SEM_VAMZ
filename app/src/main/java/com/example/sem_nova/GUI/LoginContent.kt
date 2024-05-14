package com.example.sem_nova.GUI

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_nova.GUI.ReceivedOrderScreen.TextTitle
import com.example.sem_nova.Navigation.NavigationDestination
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

/**
 * Unikátna cesta pre LoginContent
 */

object LoginDestination : NavigationDestination {
    override val route = "login"
}

/**
 * funkcia na zobrazenie UI prihlasovacej obrazovky
 */

@Composable
fun LoginContent(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    // rememberSaveable - sluzi na zapamätanie obsahu aj pri zmene orientácie aplikácie
    var LoginText by rememberSaveable { mutableStateOf(context.getString(R.string.login_hint)) }
    var PasswdText by rememberSaveable { mutableStateOf(context.getString(R.string.password_hint)) }
    val focusRequester = remember { FocusRequester() }
    val customFont = LocalCustomFont.current
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    // list medzier na základe orientácie aplikácie
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(155.dp, 50.dp, 16.dp, 90.dp) // portrait orientation
        } else {
            listOf(50.dp, 25.dp, 20.dp, 240.dp) // landscape orientation
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(spacerList[0]))

        // funkcia na zobrazenie textu na základe zadaného parametra - reťazca
        TextTitle(
            customFont = customFont,
            text = stringResource(id = R.string.welcome_hint)
        )

        Spacer(modifier = Modifier.height(spacerList[1]))

        // textfield pre zadavanie loginu
        TextField(
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            value = LoginText,
            onValueChange = { newText ->
                LoginText = newText
            },
            placeholder = {
                Text(stringResource(id = R.string._hint))
            },
            textStyle = TextStyle(
                fontFamily = customFont, // Zmena fontu
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(30.dp, 15.dp)
                .fillMaxSize()
                .focusRequester(focusRequester)
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                // logika pre zadávanie loginu do textového poľa
                .onFocusChanged { focusState ->
                    isTextFieldFocused = focusState.isFocused
                    if ((focusState.isFocused && !LoginText.isEmpty() && LoginText == context.getString(
                            R.string.login_hint
                        )) || LoginText == context.getString(
                            R.string.wrongLogin
                        )
                    ) {
                        LoginText = context.getString(R.string._hint)
                    } else if (focusState.isFocused && !LoginText.isEmpty() && LoginText != context.getString(
                            R.string.login_hint
                        )
                    ) {
                        // do no-op
                    } else if (!focusState.isFocused && LoginText.isEmpty()) {
                        LoginText = context.getString(R.string.login_hint)
                    }
                },
            shape = RoundedCornerShape(30.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        )

        // textfield pre zadavanie hesla
        TextField(
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            value = PasswdText,
            onValueChange = { newText -> PasswdText = newText },
            placeholder = {
                Text(stringResource(id = R.string._hint))
            },
            textStyle = TextStyle(
                fontFamily = customFont, // Nastavenie vlastného fontu
                fontSize = 16.sp
            ),
            // zobrazenie hesla ako ****
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(30.dp, 15.dp)
                .fillMaxSize()
                .focusRequester(focusRequester)
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                // logika pre zadávanie hesla do textového poľa
                .onFocusChanged { focusState ->
                    if (focusState.isFocused && !PasswdText.isEmpty() && PasswdText == context.getString(
                            R.string.password_hint
                        ) || PasswdText == context.getString(
                            R.string.wrongLogin
                        )
                    ) {
                        PasswdText = context.getString(R.string._hint)
                    } else if (focusState.isFocused && !PasswdText.isEmpty() && PasswdText != context.getString(
                            R.string.password_hint
                        )
                    ) {
                        // do no-op
                    } else if (!focusState.isFocused && PasswdText.isEmpty()) {
                        PasswdText = context.getString(R.string.password_hint)
                    }
                },
            shape = RoundedCornerShape(30.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        )

        Spacer(modifier = Modifier.height(spacerList[2]))

        // tlačidlo na prihlasovanie
        LoginButton(
            loginText = LoginText,
            passwdText = PasswdText,
            onLoginSuccess = onLoginSuccess,
            onLoginFailed = { // Callback - čo nastane pri nesprávnom logine
                LoginText = context.getString(R.string.wrongLogin)
                PasswdText = context.getString(R.string._hint)
                // chybová/informačná hláška
                Toast.makeText(context, R.string.wrongLogin, Toast.LENGTH_SHORT).show()
            },
            spacerList = spacerList
        )
    }
}

/**
 * funkcia na zobrazenie tlačidla na prihlasovanie + prihlasovacia logika
 */

@Composable
fun LoginButton(
    loginText: String,
    passwdText: String,
    onLoginSuccess: () -> Unit,
    onLoginFailed: () -> Unit,
    spacerList: List<Dp>
) {
    val context = LocalContext.current
    val customFont = LocalCustomFont.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(
                    222,
                    77,
                    222
                )
            ),
            // logika overovania správnosti prihlasovacích údajov
            onClick = {
                if (loginText == context.getString(R.string.correctLogin) && passwdText == context.getString(
                        R.string.correctLogin
                    )
                ) {
                    onLoginSuccess()
                } else {
                    onLoginFailed()
                }
            },
            modifier = Modifier
                .padding(spacerList[3], 16.dp)
                .fillMaxSize()
                .shadow(6.dp, shape = RoundedCornerShape(30.dp))
        ) {
            Text(
                text = context.getString(R.string.login_hint),
                fontFamily = customFont,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

