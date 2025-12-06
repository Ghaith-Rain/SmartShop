package com.ghaith.smartshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ghaith.smartshop.viewmodel.ProductViewModel
import com.ghaith.smartshop.ui.screens.ProductListScreen
import com.ghaith.smartshop.ui.screens.AddProductScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    vm: ProductViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.PRODUCT_LIST
    ) {
        // Home screen
        composable(NavRoutes.PRODUCT_LIST) {
            ProductListScreen(
                vm = vm,
                onAddClicked = { navController.navigate("add_product") },
                onProductClicked = { id -> /* TODO */ }
            )
        }

        // Add product screen
        composable(NavRoutes.ADD_PRODUCT) {
            AddProductScreen(
                vm = vm,
                onDone = { navController.popBackStack() }
            )
        }
    }
}
