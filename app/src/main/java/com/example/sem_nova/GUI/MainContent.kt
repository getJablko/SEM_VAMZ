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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_nova.R
import com.example.sem_nova.ui.theme.LocalCustomFont

@Composable
fun MainContent() {
    val customFont = LocalCustomFont.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(120.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .size(180.dp)

            ) {
                Image(
                    painter = painterResource(R.drawable.reciveorder),
                    contentDescription = (stringResource(R.string.icon)),
                    modifier = Modifier.size(140.dp)
                )
            }
            IconButton(
                onClick = { /* Handle button click */ },
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

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* Handle button click */ },
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
                text = stringResource(R.string.Logout),
                fontFamily = customFont, // vlastný font pre text
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

    }

}
