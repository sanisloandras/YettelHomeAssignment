package com.szaniszlo.yettelhomeassignment.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.szaniszlo.yettelhomeassignment.ui.countyvignettes.CountyVignettePurchaseScreen
import com.szaniszlo.yettelhomeassignment.ui.main.MainVignettePurchaseScreen
import com.szaniszlo.yettelhomeassignment.ui.orderconfirmation.OrderConfirmationScreen
import com.szaniszlo.yettelhomeassignment.ui.success.SuccessfulOrderScreen
import kotlinx.serialization.Serializable

// Routes
@Serializable
object Main
@Serializable
object YearlyCountyVignettes
@Serializable
object OrderConfirmation
@Serializable
object SuccessfulOrder

@Composable
fun YettelNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Main,
        modifier = modifier
    ) {
        composable<Main> {
            MainVignettePurchaseScreen(
                onBackClick = navController::popBackStack,
                onCountyVignettesClicked = {
                    navController.navigate(YearlyCountyVignettes)
                },
                onPurchaseClicked = {
                    navController.navigate(OrderConfirmation)
                }
            )
        }
        composable<YearlyCountyVignettes> {
            CountyVignettePurchaseScreen(
                onBack = navController::popBackStack,
                onContinue = {
                    navController.navigate(OrderConfirmation)
                }
            )
        }
        composable<OrderConfirmation> {
            OrderConfirmationScreen(
                onOrderConfirmed = {
                    navController.navigate(SuccessfulOrder) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                onCancel = navController::popBackStack
            )
        }
        composable<SuccessfulOrder> {
            SuccessfulOrderScreen(
                onOkClicked = {
                    navController.navigate(Main) {
                        popUpTo(SuccessfulOrder) { inclusive = true }
                    }
                }
            )
        }
    }
}