package com.example.sem_nova

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sem_nova.Navigation.NavigationGraph

/**
 * Top level composable that represents screens for the application.
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */
@Composable
fun WareFlowApp(navController: NavHostController = rememberNavController()) {
    NavigationGraph(navController = navController)
}
