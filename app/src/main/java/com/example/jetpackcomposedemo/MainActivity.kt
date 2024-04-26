package com.example.jetpackcomposedemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposedemo.Screen.BookQuickly.BookQuicklyScreen
import com.example.jetpackcomposedemo.Screen.BookQuickly.DiscountScreen
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.CardDetails.CardDetailScreen
import com.example.jetpackcomposedemo.Screen.Discount.CouponScreen
import com.example.jetpackcomposedemo.Screen.Discount.DiscountTopBar
import com.example.jetpackcomposedemo.Screen.Home.HomeScreen
import com.example.jetpackcomposedemo.Screen.Home.HomeTopBar
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedTopBar
import com.example.jetpackcomposedemo.Screen.Search.ListRoomScreen
import com.example.jetpackcomposedemo.Screen.Search.MethodPaymentScreen
import com.example.jetpackcomposedemo.Screen.Search.PaymentScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultFilterScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.User.InfoUser
import com.example.jetpackcomposedemo.Screen.User.LoginScreen
import com.example.jetpackcomposedemo.Screen.User.LoginViewModel
import com.example.jetpackcomposedemo.Screen.User.RegisterScreen
import com.example.jetpackcomposedemo.Screen.User.UserScreen
import com.example.jetpackcomposedemo.Screen.User.UserTopBar
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBooking.DatePickerBookingScreen
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerScreen
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DateRangePickerScreen
import com.example.jetpackcomposedemo.components.ScreenWithBottomNavigationBar
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
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
            val bookingViewModel: BookingViewModel = viewModel()

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


                //----------------------------------- Booking ------------------------------
                composable("listroom"){
                    ListRoomScreen(bookingViewModel,navController)
                }

                composable("payment"){
                    PaymentScreen(bookingViewModel,navController)
                }
                composable("methodpayment"){
                    MethodPaymentScreen(navController,bookingViewModel)

                }
                composable("bookingcalender",
                    popEnterTransition = null, popExitTransition = null, exitTransition = null, enterTransition = null
                ){
                    DatePickerBookingScreen(
                        bookingViewModel = bookingViewModel,
                        searchViewModel = searchViewModel,
                        onHandleApplyTimeBooking = {
                            navController.popBackStack()
                        })
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
                        searchViewModel = searchViewModel,
                        typeBooking = typeBooking,
                        onHandleApply = {
                            navController.popBackStack()
                        }
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
                    }, content = {padding,listState->
                        DiscountScreen(padding = padding, navController)
                    })
                }

                composable("CouponScreen"){
                    CouponScreen(navController)
                }


                //----------------------------------- USER ------------------------------
                composable("user"){
                    ScreenWithBottomNavigationBar(
                        isBotNav = !loginUiState.isShowingInfo,
                        navController = navController,
                        topBar = { UserTopBar(loginUiState = loginUiState,onLoginButtonClicked = { navController.navigate("login") }, onToogleSettingInfo = {loginViewModel1.toogleSetting(loginUiState.isShowingInfo)}) },
                        content = { padding, _ ->
                            if(loginUiState.isShowingInfo){
                                InfoUser(padding = padding)
                            }else{
                                UserScreen(padding = padding, onLogoutSuccess = { loginViewModel1.logout() }, loginUiState = loginUiState )
                            }
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
                    CardDetailScreen(bookingViewModel=bookingViewModel,cardId = cardId,navController)
                }
            }
        }
    }
}


