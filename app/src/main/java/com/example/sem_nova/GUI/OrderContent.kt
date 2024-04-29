package com.example.sem_nova.GUI

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

@Composable
fun OrderContent(onHome: () -> Unit) {
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
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

        Spacer(modifier = Modifier.height(40.dp))

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

        Spacer(modifier = Modifier.height(20.dp))

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
                text = context.getString(R.string.createOrder),
                fontFamily = customFont, // vlastný font pre text
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }


    }
}