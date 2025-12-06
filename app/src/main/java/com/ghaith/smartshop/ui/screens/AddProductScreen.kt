package com.ghaith.smartshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ghaith.smartshop.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    vm: ProductViewModel,
    onDone: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var quantityError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Product") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            // NAME FIELD
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
                Text(
                    nameError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(16.dp))

            // QUANTITY FIELD
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
                isError = quantityError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            if (quantityError != null) {
                Text(
                    quantityError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(16.dp))

            // PRICE FIELD
            OutlinedTextField(
                value = price,
                onValueChange = {
                    price = it
                    priceError = when {
                        it.isBlank() -> "Price is required"
                        it.toDoubleOrNull() == null -> "Must be a valid number"
                        it.toDouble() <= 0.0 -> "Price must be greater than 0"
                        else -> null
                    }
                },
                label = { Text("Price") },
                isError = priceError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            if (priceError != null) {
                Text(
                    priceError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(32.dp))

            val isFormValid =
                nameError == null &&
                        quantityError == null &&
                        priceError == null &&
                        name.isNotBlank() &&
                        quantity.isNotBlank() &&
                        price.isNotBlank()

            Button(
                onClick = {
                    vm.addProduct(
                        name = name,
                        quantity = quantity.toInt(),
                        price = price.toDouble()
                    )
                    onDone()
                },
                enabled = isFormValid,
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text("Save")
            }
        }
    }
}
