package com.example.sem_nova.GUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
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
fun StorageContent() {
    val customFont = LocalCustomFont.current

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

                text = stringResource(id = R.string.storage),
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