package com.roh.mathslab.ui.history

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import com.roh.mathslab.ui.a_main.MainViewModel
import com.roh.mathslab.ui.home.TestMathDao
import com.roh.mathslab.ui.home.TestMathService
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryScreenTest {

    @get:Rule
    val rule = createComposeRule()


    private val mathService = TestMathService()
    private val mathDao = TestMathDao()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(
            mathService,
            mathDao
        )
    }

    @Test
    fun initialScreen_emptyContainer() {
        rule.setContent { HistoryScreen(viewModel = viewModel) }

        rule.onNode(hasContentDescription("History_list_view"))
            .assertExists()
            .assertIsDisplayed()

    }
}