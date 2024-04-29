package com.example.sem_nova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.sem_nova.GUI.LoginContent
import com.example.sem_nova.GUI.MainContent
import com.example.sem_nova.ui.theme.SEM_NOVATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SEM_NOVATheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    //LoginScreen()
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    LoginContent()
}

@Composable
fun MainScreen() {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    MainContent()
}
