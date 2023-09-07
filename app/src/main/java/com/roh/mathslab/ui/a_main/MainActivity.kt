package com.roh.mathslab.ui.a_main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.roh.mathslab.domain.component.TabBar
import com.roh.mathslab.domain.component.TopAppBar
import com.roh.mathslab.domain.navigation.NavigationGraph
import com.roh.mathslab.ui.util.theme.MathsLabTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MAIN_ACTIVITY"

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathsLabTheme {

                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        modifier = Modifier,
                        topBar = {
                            TopAppBar()
                        }
                    ) { paddingValues ->
                        Column(
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            TabBar(
                                modifier = Modifier.fillMaxWidth(),
                                navController
                            ) { selectedTab ->
                                Log.d(TAG, "selectedTab => $selectedTab")
                                when (selectedTab) {
                                    1 -> {
                                        navController.navigate("HomeScreen") {
                                            popUpTo("HomeScreen")
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }

                                    2 -> {
                                        navController.navigate("HistoryScreen") {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            }
                            NavigationGraph(
                                navController = navController,
                                viewModel = viewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}
