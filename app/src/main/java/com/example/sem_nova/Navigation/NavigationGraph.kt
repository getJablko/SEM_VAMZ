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
import com.example.sem_nova.GUI.NewOrderScreen.NewOrderContent
import com.example.sem_nova.GUI.NewOrderScreen.NewOrderDestination
import com.example.sem_nova.GUI.OrderScreen.OrderContent
import com.example.sem_nova.GUI.OrderScreen.OrderDestination
import com.example.sem_nova.GUI.OrderDetailScreen.OrderDetailContent
import com.example.sem_nova.GUI.OrderDetailScreen.OrderDetailDestination
import com.example.sem_nova.GUI.ReceivedOrderScreen.ReceiveOrderDestination
import com.example.sem_nova.GUI.ReceivedOrderScreen.ReceiveOrderContent
import com.example.sem_nova.GUI.StorageScreen.StorageContent
import com.example.sem_nova.GUI.StorageScreen.StorageDestination


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
                onHome = {navController.navigate(MainDestination.route)},
                onNewOrder = {navController.navigate(NewOrderDestination.route)}
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
        composable(
            route = NewOrderDestination.route
        ) {
            NewOrderContent(
                onHome = { navController.navigate(OrderDestination.route) }
            )
        }

        composable(
            route = OrderDetailDestination.route
        ) {
            OrderDetailContent(
                onHome = { navController.navigate(OrderDestination.route) }
            )
        }



    }
}