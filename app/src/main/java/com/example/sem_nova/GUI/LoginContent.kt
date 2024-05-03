package com.example.sem_nova.GUI

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

@Composable
fun LoginContent(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    var LoginText by rememberSaveable { mutableStateOf(context.getString(R.string.login_hint)) }
    var PasswdText by rememberSaveable { mutableStateOf(context.getString(R.string.password_hint)) }
    val focusRequester = remember { FocusRequester() }
    val customFont = LocalCustomFont.current
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val spacerList = remember {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listOf(155.dp, 50.dp, 16.dp, 90.dp) // Heights for portrait orientation
        } else {
            listOf(10.dp, 25.dp, 20.dp, 240.dp) // Heights for landscape orientation
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        WelcomeText(customFont = customFont, spacerList = spacerList)

        Spacer(modifier = Modifier.height(spacerList[1]))

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
                fontFamily = customFont, // Zmena fontu podľa stavu
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(30.dp, 15.dp)
                .fillMaxSize()
                .focusRequester(focusRequester)
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
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
            value = PasswdText,
            onValueChange = { newText -> PasswdText = newText },
            placeholder = {
                Text(stringResource(id = R.string._hint))
            },
            textStyle = TextStyle(
                fontFamily = customFont, // Nastavenie vlastného fontu
                fontSize = 16.sp
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(30.dp, 15.dp)
                .fillMaxSize()
                .focusRequester(focusRequester)
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
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
            shape = RoundedCornerShape(30.dp)
        )

        Spacer(modifier = Modifier.height(spacerList[2]))

        LoginButton(
            loginText = LoginText,
            passwdText = PasswdText,
            onLoginSuccess = onLoginSuccess,
            onLoginFailed = { // Callback to handle incorrect login
                LoginText = context.getString(R.string.wrongLogin)
            },
            spacerList = spacerList
        )
    }
}

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
            onClick = {
                if (loginText == context.getString(R.string.correctLogin) && passwdText == context.getString(
                        R.string.correctLogin
                    )
                ) {
                    onLoginSuccess()
                } else {
                    onLoginFailed() // callback on failed login
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

@Composable
fun WelcomeText(customFont: FontFamily, spacerList: List<Dp>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(spacerList[0]))

        Box(
            modifier = Modifier.shadow(75.dp)
        ) {
            Text(
                text = stringResource(id = R.string.welcome_hint),
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
}



