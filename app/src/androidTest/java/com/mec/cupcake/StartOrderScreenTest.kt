package com.mec.cupcake

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mec.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class StartOrderScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun startOrderScreen_verifyContent() {
        val quantityOptions = listOf(
            Pair(R.string.one_cupcake, 1),
            Pair(R.string.six_cupcakes, 6),
            Pair(R.string.twelve_cupcakes, 12)
        )

        // When StartOrderScreen is loaded
        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = quantityOptions,
                onNextButtonClicked = {})
        }

        // Then verify all elements are displayed
        composeTestRule.onNodeWithTag("Cupcake Image").assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.order_cupcakes).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.six_cupcakes).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.twelve_cupcakes).assertIsDisplayed()
    }
}