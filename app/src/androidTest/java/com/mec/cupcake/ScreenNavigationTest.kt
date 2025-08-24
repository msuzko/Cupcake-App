package com.mec.cupcake

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }
    }

    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen() {
        navigateToFlavorScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    @Test
    fun cupcakeNavHost_verifyBackNavigationShownOnSelectFlavorScreen() {
        navigateToFlavorScreen()
        val backText = getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertExists()
    }

    @Test
    fun cupcakeNavHost_navigateUpFromSelectFlavorScreen_navigatesToStartOrderScreen() {
        navigateToFlavorScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelOrderOnSelectFlavorScreen_navigatesToStartOrderScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_verifyBackNavigationShownOnSelectPickupScreen() {
        navigateToPickupScreen()
        val backText = getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertExists()
    }

    @Test
    fun cupcakeNavHost_navigateUpFromSelectPickupScreen_navigatesToSelectFlavorScreen() {
        navigateToPickupScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelOrderOnSelectPickupScreen_navigatesToStartOrderScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_clickNextOnSelectPickupScreen_navigatesToSummaryScreen() {
        navigateToSummaryScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    @Test
    fun cupcakeNavHost_verifyBackNavigationShownOnSummaryScreen() {
        navigateToSummaryScreen()
        val backText = getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertExists()
    }

    @Test
    fun cupcakeNavHost_navigateUpFromSummaryScreen_navigatesToSelectPickupScreen() {
        navigateToSummaryScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelOrderOnSummaryScreen_navigatesToStartOrderScreen() {
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }


    private fun navigateToFlavorScreen() {
        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .performClick()

    }
    private fun navigateToPickupScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.chocolate)
            .performClick()
        composeTestRule.onNodeWithStringId(R.string.next)
            .performClick()
    }

    private fun navigateToSummaryScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        composeTestRule.onNodeWithStringId(R.string.next)
            .performClick()
    }

    private fun performNavigateUp() {
        val backText = getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun getString(value: Int): String {
        return composeTestRule.activity.getString(value)
    }
}