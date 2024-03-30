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
import androidx.compose.ui.Modifier
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
import com.example.jetpackcomposedemo.Screen.Search.SearchScreen
import com.example.jetpackcomposedemo.Screen.User.LoginScreen
import com.example.jetpackcomposedemo.Screen.User.RegisterScreen
import com.example.jetpackcomposedemo.Screen.User.UserScreen
import com.example.jetpackcomposedemo.Screen.User.UserTopBar
import com.example.jetpackcomposedemo.components.CalenderPicker.DateRangePickerScreen
import com.example.jetpackcomposedemo.components.ScreenWithBottomNavigationBar
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
//            JetpackComposeDemoTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    DateRangePickerScreen(true)
//                }
//            }
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
            NavHost(navController = navController, startDestination = "home" ){

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

                composable("search") {
                    SearchScreen(closeSearchScreen={
                        navController.popBackStack()
                    })
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


                //handle payload
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


