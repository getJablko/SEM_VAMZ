package com.example.sem_nova

/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SEM_NOVATheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                            LoginScreen(navController)
                        }
                        composable("main") {
                            MainScreen(navController)
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
                        composable("receive_order") {
                            ReciveOrderScreen(navController)
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
                        composable("order") {
                            OrderScreen(navController)
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
                        composable("storage") {
                            StorageScreen(navController)
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
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
    MainContent(
        onLogout = { navController.navigate("login") },
        onReceiveOrder = { navController.navigate("receive_order") },
        onNewOrder = { navController.navigate("order") },
        onStorage = { navController.navigate("storage") }
    )

}

@Composable
fun ReciveOrderScreen(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    ReceiveOrderContent(
        onHome = { navController.navigate("main") }
    )
}

@Composable
fun OrderScreen(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    OrderContent(
        onHome = { navController.navigate("main") },
        onNewOrder = {navController.navigate("new_order")}
    )
}

@Composable
fun StorageScreen(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    StorageContent(
        onHome = { navController.navigate("main") }
    )
}
*/
