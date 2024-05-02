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
import com.example.jetpackcomposedemo.Screen.GlobalScreen.VideoScreen
import com.example.jetpackcomposedemo.Screen.Home.HomeScreen
import com.example.jetpackcomposedemo.Screen.Home.HomeTopBar
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedTopBar
import com.example.jetpackcomposedemo.Screen.Search.ListRoomScreen
import com.example.jetpackcomposedemo.Screen.Search.MyBookingScreen
import com.example.jetpackcomposedemo.Screen.Search.PaymentScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.User.InfoUser
import com.example.jetpackcomposedemo.Screen.User.LoginScreen
import com.example.jetpackcomposedemo.Screen.User.LoginViewModel
import com.example.jetpackcomposedemo.Screen.User.RegisterScreen
import com.example.jetpackcomposedemo.Screen.User.UserScreen
import com.example.jetpackcomposedemo.Screen.User.UserTopBar
import com.example.jetpackcomposedemo.components.ScreenWithBottomNavigationBar
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val data = listOf(1,2,3,4,5,6)
//            LazyRow {
//                items(data.size){
//                    AnimatedShimmer(it,data)
//
//                }
//
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
            NavHost(navController = navController, startDestination = "StartingAppScreen" ){


                //////////////////////////////// my booking ///////////////////////////
                composable("mybooking"){
                    MyBookingScreen(bookingViewModel = bookingViewModel, navController =navController )
                }

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
                        content = { padding,listState->
                            HomeScreen(
                                navController = navController,
                                padding = padding,
                                listState=listState,
                                onOpenScreenSearch = {
                                    navController.navigate("search")
                                },
                                onOpenDetailCardScreen = {roomId->
                                    navController.navigate("roomDetails/$roomId")
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
                        onHandleSearchClickButtonSearch = {filter->
                            navController.navigate("search/$filter")
                        },
                        closeSearchScreen={
                            navController.popBackStack("home",inclusive = false)
                        })
                }
                //--------------------------------search result -------------------------------------------
                composable(
                    "search/{filter}",
                    arguments = listOf(
                        navArgument("filter") {
                            type = NavType.StringType
                            defaultValue = null
                            nullable = true
                        })
                ){backStackEntry ->

                    val typeBooking = backStackEntry.arguments?.getString("filter").toString()
                    SearchResultScreen(
                        typeBooking = typeBooking,
                        searchViewModel = searchViewModel,
                        onBackSearchScreen = {
                            navController.popBackStack()
                        },
                        onOpenSearchScreen = {
                            navController.popBackStack("search",inclusive = false)
                        }
                    )
                }

                //----------------------------------- PROPOSED ------------------------------
                composable("proposed"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar = { ProposedTopBar() },
                        content = { padding, _ ->
                            ProposedScreen(padding = padding, onOpenDetailCardScreen = {roomId->
                                navController.navigate("roomDetails/$roomId")
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
                    }, content = {padding, _->
                        DiscountScreen(padding = padding, navController)
                    })
                }

                composable("CouponScreen"){
                    CouponScreen(navController, 1)
                }

                composable("StartingAppScreen"){
                    VideoScreen(navController)
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
                    "roomDetails/{roomId}",
                    arguments = listOf(
                        navArgument("roomId") {
                            type = NavType.StringType
                        })
                ) { backStackEntry ->

                    val roomId = backStackEntry.arguments?.getString("roomId")
                    CardDetailScreen(
                        searchViewModel = searchViewModel,
                        bookingViewModel=bookingViewModel,
                        roomId = roomId,
                        onOpenListRoom = {
                            navController.navigate("roomDetails/$roomId/listroom")
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                //----------------------------------- Booking ------------------------------
                composable(
                    "roomDetails/{roomId}/listroom",
                    arguments = listOf(
                        navArgument("roomId") {
                            type = NavType.StringType
                        })
                ){ backStackEntry ->

                    val roomId = backStackEntry.arguments?.getString("roomId")
                    ListRoomScreen(
                        searchViewModel = searchViewModel,
                        bookingViewModel,
                        onOpenPayment = {
                            navController.navigate("roomDetails/$roomId/payment")
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    "roomDetails/{roomId}/payment",
                    arguments = listOf(
                        navArgument("roomId") {
                            type = NavType.StringType
                        })
                ){ backStackEntry ->

                    val roomId = backStackEntry.arguments?.getString("roomId")
                    PaymentScreen(bookingViewModel,navController)
                }
            }
        }
    }
}


