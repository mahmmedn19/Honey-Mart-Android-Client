package org.the_chance.honeymart.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.navigation.RootNavigationGraph
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honeymart.ui.navigation.navigation_rail.NavigationRail
import org.the_chance.honymart.ui.theme.HoneyMartTheme


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
                HoneyMartTheme {
                    val navigationRailState = checkNavigationRailState()
                    Row {
                        AnimatedVisibility(
                            visible = navigationRailState.value,
                            enter = slideInHorizontally { -it },
                            exit = slideOutHorizontally { -it }
                        ) {
                            NavigationRail()
                        }
                        RootNavigationGraph()
                    }
                }
            }
        }
    }

    @Composable
    private fun checkNavigationRailState(): MutableState<Boolean> {
        val navController = LocalNavigationProvider.current
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val navigationRailState = rememberSaveable { (mutableStateOf(false)) }

        val navigationRailScreens = listOf(
            Screen.Category.route,
            Screen.Orders.route,
        )
        when (navBackStackEntry?.destination?.route) {
            in navigationRailScreens -> {
                navigationRailState.value = true
            }

            else -> {
                navigationRailState.value = false
            }
        }
        return navigationRailState
    }
}