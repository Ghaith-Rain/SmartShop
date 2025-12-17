package com.ghaith.smartshop.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ghaith.smartshop.data.local.Product
import com.ghaith.smartshop.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    vm: ProductViewModel,
    onAddClicked: () -> Unit,
    onProductClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Products") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClicked) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { paddingValues ->

        ProductListContent(
            vm = vm,
            onProductClicked = onProductClicked,
            onEditClicked = onEditClicked,  // <-- PASS IT HERE
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ProductListContent(
    vm: ProductViewModel,
    onProductClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val products by vm.products.collectAsState()

    if (products.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No products yet")
        }
    } else {
        LazyColumn(modifier = modifier.padding(16.dp)) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onClick = { onProductClicked(product.id) },
                    onDelete = { vm.delete(product) },
                    onEdit = { onEditClicked(product.id) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit   // <-- FIXED SIGNATURE
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(product.name, style = MaterialTheme.typography.titleMedium)
                Text("Qty: ${product.quantity}")
                Text("Price: ${product.price}")
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }

                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
