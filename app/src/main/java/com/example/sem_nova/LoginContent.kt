package com.example.sem_nova

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_nova.ui.theme.LocalCustomFont

@Composable
fun LoginContent() {
    val context = LocalContext.current
    var LoginText by remember { mutableStateOf(context.getString(R.string.login_hint)) }
    var PasswdText by remember { mutableStateOf(context.getString(R.string.password_hint)) }
    val focusRequester = remember { FocusRequester() }
    val customFont = LocalCustomFont.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = stringResource(id = R.string.welcome_hint),
            fontFamily = customFont,
            color = Color.White,
            fontSize = 38.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                lineHeight = 40.sp,
        )
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = LoginText,
            onValueChange = { newText -> LoginText = newText },
            placeholder = {
                Text(stringResource(id = R.string.login_hint))
            },
            modifier = Modifier
                .padding(45.dp, 15.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        LoginText = context.getString(R.string._hint)
                    } else if (LoginText.isEmpty()) {
                        LoginText = context.getString(R.string.login_hint)
                    }
                }
        )

        TextField(
            value = PasswdText,
            onValueChange = { newText -> PasswdText = newText },
            placeholder = {
                Text(stringResource(id = R.string.password_hint))
            },
            modifier = Modifier
                .padding(45.dp, 15.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        PasswdText = context.getString(R.string._hint)
                    } else if (PasswdText.isEmpty()) {
                        PasswdText = context.getString(R.string.password_hint)
                    }
                }
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { /* Handle login button click */ },
            modifier = Modifier
                .padding(45.dp, 15.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {

            Text(
                text = context.getString(R.string.login_hint),
                fontFamily = customFont, // Použiť vlastný font pre text
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}