package com.roh.mathslab.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roh.mathslab.ui.a_main.MainViewModel
import com.roh.mathslab.ui.history.HistoryScreen
import com.roh.mathslab.ui.home.HomeScreen

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel,
) {

    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(
                viewModel = viewModel,
            )

        }

        composable("HistoryScreen") {
            HistoryScreen(
                viewModel = viewModel
            )
        }
    }

}