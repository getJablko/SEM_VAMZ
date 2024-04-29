package com.example.sem_nova.GUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont
import androidx.compose.ui.platform.LocalContext
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun ReciveOrderContent() {
    val context = LocalContext.current
    val customFont = LocalCustomFont.current
    val focusRequester = remember { FocusRequester() }
    var isTextFieldFocused by remember { mutableStateOf(false) }
    var RecivedOrderNumberText by remember {
        mutableStateOf(context.getString(R.string.recivedOrderNumberOrder))
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
            IconButton(
                onClick = { /* Handle button click */ },
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
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(130.dp))

        Box(
            modifier = Modifier.shadow(75.dp)
        ) {
            Text(

                text = stringResource(id = R.string.welcomeBack_hint),
                fontFamily = customFont,
                color = Color.White,
                fontSize = 42.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    lineHeight = 42.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

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
                            R.string.recivedOrderNumberOrder
                        )
                    ) {
                        RecivedOrderNumberText = context.getString(R.string._hint)
                    } else if (focusState.isFocused && !RecivedOrderNumberText.isEmpty() && RecivedOrderNumberText != context.getString(
                            R.string.recivedOrderNumberOrder
                        )
                    ) {
                        // do nothing
                    } else if (!focusState.isFocused && RecivedOrderNumberText.isEmpty()) {
                        RecivedOrderNumberText =
                            context.getString(R.string.recivedOrderNumberOrder)
                    }
                },
            shape = RoundedCornerShape(30.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(222, 77, 222) // Farba obsahu v normálnom stave
            ),
            onClick = { /* Handle login button click */ },
            modifier = Modifier
                .padding(46.dp, 15.dp)
                .fillMaxWidth()
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                .height(45.dp)
        ) {
            Text(
                text = context.getString(R.string.reciveOrder),
                fontFamily = customFont, // vlastný font pre text
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        IconButton(
            onClick = { /* Handle button click */ },
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
}