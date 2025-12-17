package com.ghaith.smartshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ghaith.smartshop.viewmodel.ProductViewModel
import com.ghaith.smartshop.ui.screens.ProductListScreen
import com.ghaith.smartshop.ui.screens.AddProductScreen
import com.ghaith.smartshop.ui.screens.EditProductScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    vm: ProductViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "product_list"
    ) {
        // Home screen
        composable("product_list") {
            ProductListScreen(
                vm = vm,
                onAddClicked = { navController.navigate("add_product") },
                onProductClicked = { id -> /* optional */ },
                onEditClicked = { id -> navController.navigate("edit_product/$id") }
            )
        }

        // Add product screen
        composable("add_product") {
            AddProductScreen(
                vm = vm,
                onDone = { navController.popBackStack() }
            )
        }

        composable(
            route = "edit_product/{id}"
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id")!!

            EditProductScreen(
                vm = vm,
                productId = id,
                onDone = { navController.popBackStack() }
            )
        }

    }
}
