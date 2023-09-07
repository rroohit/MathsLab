package com.roh.mathslab.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import com.roh.mathslab.domain.model.ExpressionDetails
import com.roh.mathslab.ui.a_main.MainViewModel
import junit.framework.TestCase.assertEquals

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

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
        rule.setContent { HomeScreen(viewModel = viewModel) }

        rule.onNode(hasContentDescription("Edit_box_view"))
            .assertExists()
            .assertIsDisplayed()

        rule.onNode(hasContentDescription("Edit_Text_View"))
            .assertExists()
            .assertIsDisplayed()

        rule.onNode(hasContentDescription("Home_Screen_Container"))
            .assertExists()
            .assertIsDisplayed()

        rule.onNode(hasContentDescription("Get_result_button"))
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun emptyList() {
        rule.setContent { HomeScreen(viewModel = viewModel) }

        assertEquals(emptyList<ExpressionDetails>(), viewModel.currentList.value)

        assertEquals(false, viewModel.isProcessing.value)

    }

}