package com.example.sem_nova.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import com.example.sem_nova.GUI.LoginContent
import com.example.sem_nova.GUI.LoginDestination
import com.example.sem_nova.GUI.MainContent
import com.example.sem_nova.GUI.MainDestination
import com.example.sem_nova.GUI.OrderContent
import com.example.sem_nova.GUI.OrderDestination
import com.example.sem_nova.GUI.ReceiveOrderDestination
import com.example.sem_nova.GUI.ReceiveOrderContent
import com.example.sem_nova.GUI.StorageContent
import com.example.sem_nova.GUI.StorageDestination


@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginContent(
                onLoginSuccess = { navController.navigate(MainDestination.route) },
            )
        }
        composable(route = MainDestination.route) {

            MainContent(
                onLogout = {navController.navigate(LoginDestination.route)},
                onNewOrder = {navController.navigate(OrderDestination.route)},
                onReceiveOrder = {navController.navigate(ReceiveOrderDestination.route)},
                onStorage = {navController.navigate(StorageDestination.route)}
            )
        }
        /*
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemDetailsScreen(
                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }

         */
        composable(
            route = OrderDestination.route
        ) {
            OrderContent(
                onHome = {navController.navigate(MainDestination.route)}
            )
        }
        composable(
            route = ReceiveOrderDestination.route
        ) {
            ReceiveOrderContent(
                onHome = {navController.navigate(MainDestination.route)}
            )
        }
        composable(
            route = StorageDestination.route
        ) {
            StorageContent(
                onHome = {navController.navigate(MainDestination.route)}
            )
        }



    }
}