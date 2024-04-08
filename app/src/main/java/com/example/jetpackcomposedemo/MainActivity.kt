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
import com.example.jetpackcomposedemo.Screen.CardDetails.CardDetailScreen
import com.example.jetpackcomposedemo.Screen.Discount.DiscountTopBar
import com.example.jetpackcomposedemo.Screen.Home.HomeScreen
import com.example.jetpackcomposedemo.Screen.Home.HomeTopBar
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedTopBar
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.User.LoginScreen
import com.example.jetpackcomposedemo.Screen.User.RegisterScreen
import com.example.jetpackcomposedemo.Screen.User.UserScreen
import com.example.jetpackcomposedemo.Screen.User.UserTopBar
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
fun MainApp(){
    val navController = rememberNavController()
    JetpackComposeDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val searchViewModel: SearchViewModel = viewModel()
            NavHost(navController = navController, startDestination = "home" ){


                //search result
                composable("search/result"){
                    SearchResultScreen()
                }

                //Home Screen
                composable("home"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar ={listState-> HomeTopBar(listState,onOpenScreenSearch ={
                            navController.navigate("search")
                        }) } ,
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

                // search widget
                composable("search") {
                    SearchScreen(
                        searchViewModel = searchViewModel,
                        onOpenDatePickerScreen = {typeBooking->
                            navController.navigate("search/$typeBooking/calender")
                        },
                        closeSearchScreen={
                            navController.popBackStack()
                        })
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
                            onCloseCalenderScreen = {
                                showCalender.value = false
                                navController.popBackStack(route = "search",inclusive = false)
                            })
                        "overnight"-> DateRangePickerScreen(
                            searchViewModel = searchViewModel,
                            typeBooking = typeBooking,
                            visible = showCalender.value,
                            onCloseCalenderScreen = {
                                showCalender.value = false
                                navController.popBackStack(route = "search",inclusive = false)
                            }
                        )
                        "bydate"-> DateRangePickerScreen(
                            searchViewModel = searchViewModel,
                            typeBooking = typeBooking,
                            visible = showCalender.value,
                            onCloseCalenderScreen = {
                                showCalender.value = false
                                navController.popBackStack(route = "search",inclusive = false)
                            }
                        )
                    }
                }


                // Đề xuất Screen
                composable("proposed"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar = { ProposedTopBar() },
                        content = {padding,listState->
                            ProposedScreen(padding = padding, onOpenDetailCardScreen = {cardId->
                                navController.navigate("carddetail/$cardId")
                            })
                        })
                }

                //Đặt phòng nhanh Screen
                composable("bookquickly"){
                    ScreenWithBottomNavigationBar(navController = navController, content = {padding,listState->
                        BookQuicklyScreen(padding = padding)
                    })
                }

                //Ưu đãi Screen
                composable("discount"){
                    ScreenWithBottomNavigationBar(navController = navController, topBar = {
                        DiscountTopBar()
                    }, content = {padding,listState->
                        DiscountScreen(padding = padding)
                    })
                }

                //user Screen
                composable("user"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar = { UserTopBar(onLoginButtonClicked = { navController.navigate("login") }) },
                        content = {padding,listState->
                            UserScreen(padding = padding, )
                        })
                }

                composable("login") {
                    LoginScreen(
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

                //handle payload card
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


