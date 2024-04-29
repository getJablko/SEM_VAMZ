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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sem_nova.GUI.LoginContent
import com.example.sem_nova.GUI.MainContent
import com.example.sem_nova.GUI.OrderContent
import com.example.sem_nova.GUI.ReciveOrderContent
import com.example.sem_nova.GUI.StorageContent
import com.example.sem_nova.ui.theme.SEM_NOVATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SEM_NOVATheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController) }
                        composable("main") { MainScreen(navController) }
                        composable("receive_order") { ReciveOrderScreen(navController) }
                        composable("order") { OrderScreen(navController) }
                        composable("storage") { StorageScreen(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    LoginContent(onLoginSuccess = {
        navController.navigate("main")
    })
}

@Composable
fun MainScreen(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    MainContent(onLogout = {
        navController.navigate("login")
    })

}

@Composable
fun ReciveOrderScreen(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    ReciveOrderContent()
}

@Composable
fun OrderScreen(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    OrderContent()
}

@Composable
fun StorageScreen(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    StorageContent()
}
