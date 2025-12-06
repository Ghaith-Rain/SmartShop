package com.ghaith.smartshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ghaith.smartshop.data.local.Product
import com.ghaith.smartshop.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    vm: ProductViewModel,
    productId: Long,
    onDone: () -> Unit
) {
    val products by vm.products.collectAsState()
    val product = products.find { it.id == productId }

    if (product == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Product not found")
        }
        return
    }

    var name by remember { mutableStateOf(product.name) }
    var quantity by remember { mutableStateOf(product.quantity.toString()) }
    var price by remember { mutableStateOf(product.price.toString()) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var quantityError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Product") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = if (it.isBlank()) "Name cannot be empty" else null
                },
                label = { Text("Product Name") },
                isError = nameError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (nameError != null) {
                Text(nameError!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = quantity,
                onValueChange = {
                    quantity = it
                    quantityError = when {
                        it.isBlank() -> "Quantity is required"
                        it.toIntOrNull() == null -> "Must be a number"
                        it.toInt() < 0 -> "Quantity cannot be negative"
                        else -> null
                    }
                },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = quantityError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (quantityError != null) {
                Text(quantityError!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = price,
                onValueChange = {
                    price = it
                    priceError = when {
                        it.isBlank() -> "Price is required"
                        it.toDoubleOrNull() == null -> "Must be a valid number"
                        it.toDouble() <= 0 -> "Price must be > 0"
                        else -> null
                    }
                },
                label = { Text("Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = priceError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (priceError != null) {
                Text(priceError!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(32.dp))

            val isFormValid =
                nameError == null &&
                        quantityError == null &&
                        priceError == null

            Button(
                onClick = {
                    val updated = product.copy(
                        name = name,
                        quantity = quantity.toInt(),
                        price = price.toDouble()
                    )
                    vm.update(updated)
                    onDone()
                },
                enabled = isFormValid,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save Changes")
            }
        }
    }
}
