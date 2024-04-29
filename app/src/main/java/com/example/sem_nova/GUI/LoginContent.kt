package com.example.sem_nova.GUI

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

@Composable
fun LoginContent() {
    val context = LocalContext.current
    var LoginText by remember { mutableStateOf(context.getString(R.string.login_hint)) }
    var PasswdText by remember { mutableStateOf(context.getString(R.string.password_hint)) }
    val focusRequester = remember { FocusRequester() }
    val customFont = LocalCustomFont.current
    var isTextFieldFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(150.dp))

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

        Spacer(modifier = Modifier.height(75.dp))

        TextField(
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
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
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                .onFocusChanged { focusState ->
                    isTextFieldFocused = focusState.isFocused
                    if (focusState.isFocused && !LoginText.isEmpty() && LoginText == context.getString(
                            R.string.login_hint
                        )
                    ) {
                        LoginText = context.getString(R.string._hint)
                    } else if (focusState.isFocused && !LoginText.isEmpty() && LoginText != context.getString(
                            R.string.login_hint
                        )
                    ) {
                        // do nothing
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
            value = PasswdText,
            onValueChange = { newText -> PasswdText = newText },
            placeholder = {
                Text(stringResource(id = R.string._hint))
            },
            textStyle = TextStyle(
                fontFamily = customFont, // Nastavenie vlastného fontu
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(30.dp, 15.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                .onFocusChanged { focusState ->
                    if (focusState.isFocused && !PasswdText.isEmpty() && PasswdText == context.getString(
                            R.string.password_hint
                        )
                    ) {
                        PasswdText = context.getString(R.string._hint)
                    } else if (focusState.isFocused && !PasswdText.isEmpty() && PasswdText != context.getString(
                            R.string.password_hint
                        )
                    ) {
                        // do nothing
                    } else if (!focusState.isFocused && PasswdText.isEmpty()) {
                        PasswdText = context.getString(R.string.password_hint)
                    }
                },
            shape = RoundedCornerShape(30.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(222, 77, 222) // Farba obsahu v normálnom stave
            ),
            onClick = { /* Handle login button click */ },
            modifier = Modifier
                .padding(75.dp, 15.dp)
                .fillMaxWidth()
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                .height(45.dp)
        ) {

            Text(
                text = context.getString(R.string.login_hint),
                fontFamily = customFont, // vlastný font pre text
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}