package com.roh.mathslab.domain.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roh.mathslab.R
import com.roh.mathslab.ui.util.theme.Purple40


@Composable
fun TabBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onTabSelected: (Int) -> Unit
) {

    var selectedTab by remember {
        mutableIntStateOf(1)
    }

    LaunchedEffect(key1 = true) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                "HomeScreen" -> {
                    selectedTab = 1
                }

                "HistoryScreen" -> {
                    selectedTab = 2
                }
            }
        }
    }

    Row(
        modifier = modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        TabItem(
            Modifier.weight(0.5f),
            tabName = stringResource(id = R.string.evaluate),
            (selectedTab == 1)
        ) {
            selectedTab = 1
            onTabSelected(selectedTab)
        }

        TabItem(
            Modifier.weight(0.5f),
            tabName = stringResource(id = R.string.history),
            (selectedTab == 2)
        ) {
            selectedTab = 2
            onTabSelected(selectedTab)
        }
    }
}

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    tabName: String,
    selectedTab: Boolean,
    onClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.Transparent)
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = tabName,
            color = if (selectedTab) Purple40 else Color.LightGray,
            fontWeight = if (selectedTab) FontWeight.Bold else FontWeight.Normal,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .height(2.5.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(2.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
           this@Column.AnimatedVisibility(
               visible = selectedTab,
               enter = scaleIn(),
               exit = scaleOut(animationSpec = tween(700)) +
                       shrinkHorizontally(animationSpec = tween(700))
           ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Purple40,
                            shape = RoundedCornerShape(2.dp)
                        )

                )
            }
        }
    }
}