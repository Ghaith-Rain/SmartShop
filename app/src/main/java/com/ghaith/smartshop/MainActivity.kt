package com.ghaith.smartshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ghaith.smartshop.data.local.AppDatabase
import com.ghaith.smartshop.data.remote.FirestoreSync
import com.ghaith.smartshop.data.repository.ProductRepository
import com.ghaith.smartshop.viewmodel.ProductViewModel
import com.ghaith.smartshop.viewmodel.ProductViewModelFactory
import com.ghaith.smartshop.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FirestoreSync().testConnection()
        // 1) Build DB
        val db = AppDatabase.getInstance(applicationContext)

        // 2) Build repo
        val repo = ProductRepository(db.productDao())

        // 3) Build ViewModel using factory
        val vm: ProductViewModel by viewModels {
            ProductViewModelFactory(repo)
        }

        setContent {
            SmartShopApp(vm)
        }
    }
}

@Composable
fun SmartShopApp(vm: ProductViewModel) {
    val navController = rememberNavController()

    Surface(color = MaterialTheme.colorScheme.background) {
        AppNavHost(navController = navController, vm = vm)
    }
}
