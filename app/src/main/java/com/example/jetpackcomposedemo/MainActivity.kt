package com.example.jetpackcomposedemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.jetpackcomposedemo.Screen.BookQuickly.BookQuicklyScreen
import com.example.jetpackcomposedemo.Screen.BookQuickly.DiscountScreen
import com.example.jetpackcomposedemo.Screen.CardDetails.CardDetailScreen
import com.example.jetpackcomposedemo.Screen.Discount.DiscountTopBar
import com.example.jetpackcomposedemo.Screen.Home.HomeScreen
import com.example.jetpackcomposedemo.Screen.Home.HomeTopBar
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedTopBar
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultFilterScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.User.LoginScreen
import com.example.jetpackcomposedemo.Screen.User.LoginViewModel
import com.example.jetpackcomposedemo.Screen.User.RegisterScreen
import com.example.jetpackcomposedemo.Screen.User.UserScreen
import com.example.jetpackcomposedemo.Screen.User.UserTopBar
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerScreen
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DateRangePickerScreen
import com.example.jetpackcomposedemo.components.ScreenWithBottomNavigationBar
import com.example.jetpackcomposedemo.data.models.Product
import com.example.jetpackcomposedemo.data.network.RetrofitInstance
import com.example.jetpackcomposedemo.data.repository.ProductsRepositoryImpl
import com.example.jetpackcomposedemo.data.viewmodel.ProductsViewModel
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

class MainActivity : ComponentActivity() {

//    private val viewModel by viewModels<ProductsViewModel> (factoryProducer = {
//        object : ViewModelProvider.Factory{
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return ProductsViewModel(ProductsRepositoryImpl(RetrofitInstance.apiService))
//                        as T
//            }
//        }
//    })

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
//        setContent {
//            val productList = viewModel.product.collectAsState().value
//            val context = LocalContext.current
//            LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
//                viewModel.showErrorToastChannel.collectLatest { show->
//                    if(show){
//                        Toast.makeText(
//                            context,"Error",Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//            }
//
//            if(productList.isEmpty()){
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//                }
//            }
//            else{
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    contentPadding = PaddingValues(16.dp)
//                ) {
//                    items(productList.size) { index ->
//                        Product(productList[index])
//                        Spacer(modifier = Modifier.height(16.dp))
//                    }
//                }
//            }
//
//        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(
    loginViewModel1: LoginViewModel = viewModel()
){
    val navController = rememberNavController()
    JetpackComposeDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val searchViewModel: SearchViewModel = viewModel()
            val loginUiState by loginViewModel1.uiState.collectAsState()
            NavHost(navController = navController, startDestination = "home" ){

                //----------------------------------- HOME ------------------------------
                composable("home"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar ={listState-> HomeTopBar(listState,
                            onOpenNotifications = {
                                navController.navigate("notification")
                            },
                            onOpenScreenSearch ={
                                navController.navigate("search")
                            }
                        ) } ,
                        content ={ padding,listState->
                            HomeScreen(
                                padding = padding,
                                listState=listState,
                                onOpenScreenSearch = {
                                    navController.navigate("search")
                                },
                                onOpenDetailCardScreen = {cardId->
                                    navController.navigate("carddetail/$cardId")
                                })
                        })
                }

                //----------------------------------- NOTIFICATION ------------------------------
                composable("notification"){
                    NotificationsScreen(navController = navController)
                }

                //----------------------------------- SEARCH ------------------------------
                composable("search") {
                    SearchScreen(
                        searchViewModel = searchViewModel,
                        onOpenDatePickerScreen = {typeBooking->
                            navController.navigate("search/$typeBooking/calender")
                        },
                        onHandleSearchClickButton = {typeBooking->
                            navController.navigate("search/$typeBooking/result")
                        },
                        closeSearchScreen={
                            navController.popBackStack("home",inclusive = false)
                        })
                }
                //search result
                composable(
                    "search/{typeBooking}/result",
                    arguments = listOf(
                        navArgument("typeBooking") {
                            type = NavType.StringType
                        })
                ){backStackEntry ->

                    val typeBooking = backStackEntry.arguments?.getString("typeBooking").toString()
                    SearchResultScreen(
                        typeBooking = typeBooking,
                        searchViewModel = searchViewModel,
                        onBackSearchScreen = {
                            navController.popBackStack()
                        },
                        onOpenSearchScreen = {
                            navController.popBackStack("search",inclusive = false)
                        },
                        onOpenFilter = {
                            navController.navigate("search/$typeBooking/filter")
                        }
                    )
                }

                composable(
                    "search/{typeBooking}/filter",
                    arguments = listOf(
                        navArgument("typeBooking") {
                            type = NavType.StringType
                        })
                ){backStackEntry->
                    val typeBooking = backStackEntry.arguments?.getString("typeBooking").toString()
                    SearchResultFilterScreen(
                        typeBooking = typeBooking
                    ) {
                        navController.popBackStack()
                    }
                }

                composable(
                    "search/{typeBooking}/calender",
                    arguments = listOf(
                        navArgument("typeBooking") {
                            type = NavType.StringType
                        }),
                    enterTransition = null,
                    exitTransition =null,
                    popEnterTransition = null,
                    popExitTransition = null
                ) { backStackEntry ->

                    val typeBooking = backStackEntry.arguments?.getString("typeBooking")
                    val showCalender = remember {
                        mutableStateOf(true)
                    }
                    when(typeBooking){
                        "hourly"-> DatePickerScreen(
                            searchViewModel = searchViewModel,
                            typeBooking = typeBooking,
                            visible = showCalender.value,
                            onHandleClickButtonDelete = {
                                navController.popBackStack(route = "search",inclusive = false)

                            },
                            onCloseCalenderScreen = {
                                showCalender.value = false
                                navController.popBackStack(route = "search",inclusive = false)
                            })
                        "overnight"-> DateRangePickerScreen(
                            searchViewModel = searchViewModel,
                            typeBooking = typeBooking,
                            visible = showCalender.value,
                            onHandleClickButtonDelete = {
                                navController.popBackStack(route = "search",inclusive = false)

                            },
                            onCloseCalenderScreen = {
                                showCalender.value = false
                                navController.popBackStack(route = "search",inclusive = false)
                            }
                        )
                        "bydate"-> DateRangePickerScreen(
                            searchViewModel = searchViewModel,
                            typeBooking = typeBooking,
                            visible = showCalender.value,
                            onHandleClickButtonDelete = {
                                navController.popBackStack(route = "search",inclusive = false)

                            },
                            onCloseCalenderScreen = {
                                showCalender.value = false
                                navController.popBackStack(route = "search",inclusive = false)
                            }
                        )
                    }
                }


                //----------------------------------- PROPOSED ------------------------------
                composable("proposed"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar = { ProposedTopBar() },
                        content = { padding, _ ->
                            ProposedScreen(padding = padding, onOpenDetailCardScreen = {cardId->
                                navController.navigate("carddetail/$cardId")
                            })
                        })
                }

                //----------------------------------- BOOKQUICKLY ------------------------------
                composable("bookquickly"){
                    ScreenWithBottomNavigationBar(navController = navController, content = { padding, _ ->
                        BookQuicklyScreen(padding = padding)
                    })
                }

                //----------------------------------- DISCOUNT ------------------------------
                composable("discount"){
                    ScreenWithBottomNavigationBar(navController = navController, topBar = {
                        DiscountTopBar()
                    }, content = { padding, _ ->
                        DiscountScreen(padding = padding)
                    })
                }

                //----------------------------------- USER ------------------------------
                composable("user"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar = { UserTopBar(loginUiState = loginUiState,onLoginButtonClicked = { navController.navigate("login") }) },
                        content = { padding, _ ->
                            UserScreen(padding = padding )
                        })
                }

                composable("login") {
                    LoginScreen(
                        loginViewModel1,
                        onCancelButtonClicked = {
                            navController.popBackStack("user", inclusive = false)
                        },
                        onClickedRegisterText = {
                            navController.navigate("register")
                        }
                    )

                }

                composable("register") {
                    RegisterScreen(
                        onCancelButtonClicked = {
                            navController.popBackStack()
                        },
                        onClickedLoginText = {
                            navController.navigate("login")
                        }
                    )
                }

                //----------------------------------- PAYLOAD CARD ------------------------------
                composable(
                    "carddetail/{cardId}",
                    arguments = listOf(
                        navArgument("cardId") {
                            type = NavType.StringType
                        })
                ) { backStackEntry ->

                    val cardId = backStackEntry.arguments?.getString("cardId")
                    CardDetailScreen(cardId = cardId,navController)
                }
            }
        }
    }
}


