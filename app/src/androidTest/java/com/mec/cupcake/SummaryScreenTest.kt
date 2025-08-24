package com.mec.cupcake

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mec.cupcake.data.OrderUiState
import com.mec.cupcake.ui.OrderSummaryScreen
import org.junit.Rule
import org.junit.Test

class SummaryScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun summaryScreen_verifyContent() {
        val price = "2"
        val quantity = 1
        val flavor = "Vanilla"
        val pickup_date = "Sun Aug 24"
        val orderUiState = OrderUiState(quantity, flavor, pickup_date, price)
        val numberOfCupcakes = composeTestRule.activity.resources.getQuantityString(
            R.plurals.cupcakes,
            orderUiState.quantity,
            orderUiState.quantity
        )
        val items = listOf(
            // Summary line 1: display selected quantity
            Pair(getString(R.string.quantity), numberOfCupcakes),
            // Summary line 2: display selected flavor
            Pair(getString(R.string.flavor), flavor),
            // Summary line 3: display selected pickup date
            Pair(getString(R.string.pickup_date), pickup_date)
        )
        // Test implementation goes here
        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = orderUiState,
                onCancelButtonClicked = {},
                onSendButtonClicked = { _, _ -> }
            )
        }

        // Add assertions to verify the content of the summary screen
        items.forEach {
            composeTestRule.onNodeWithText(it.first.uppercase()).assertIsDisplayed()
            composeTestRule.onNodeWithText(it.second).assertIsDisplayed()
        }
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.subtotal_price,
                price
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.send).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cancel).assertIsDisplayed()
    }
    
    private fun getString(@StringRes id: Int): String {
        return composeTestRule.activity.getString(id)
    }
}