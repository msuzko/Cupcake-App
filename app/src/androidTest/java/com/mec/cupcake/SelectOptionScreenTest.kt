package com.mec.cupcake

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mec.cupcake.ui.SelectOptionScreen
import org.junit.Rule
import org.junit.Test

class SelectOptionScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        // Given list of options
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        // And subtotal
        val subtotal = "$100"

        // When SelectOptionScreen is loaded
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }
        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.subtotal_price, subtotal
            )
        ).assertIsDisplayed()

        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.chocolate)
            .performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

}