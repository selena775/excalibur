package com.example.excalibur

import HomeScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.excalibur.ui.theme.ExcaliburTheme
import kotlinx.serialization.Serializable
import java.util.Map.entry

sealed class Screen : NavKey {

    @Serializable
    data object Home : Screen()

    @Serializable
    data object Products : Screen()

    @Serializable
    data class Product(val productName: String) : Screen()

    // A simple property to get a displayable name
    val name: String
        get() = when (this) {
            Home -> "Home"
            Products -> "Products"
            is Product -> "Product $productName"
        }

    // A simple property to get a displayable name
    val icon: ImageVector
        get() = when (this) {
            Home -> Icons.Default.Home
            Products -> Icons.Default.ShoppingCart
            is Product -> Icons.Default.Favorite
        }

    val screensToShow: List<Screen>
        get() = listOf(Screen.Home, Screen.Products, Product("None"))
}


@Composable
fun NavExample(backStack: SnapshotStateList<NavKey>, modifier: Modifier) {

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.Home> { key ->
                HomeScreen("Welcome to Nav3", onNextScreen = {
                    backStack.add(Screen.Products)
                })
            }
            entry<Screen.Products> { key ->
                val itemsList = listOf(
                    "Apple",
                    "Banana",
                    "Cherry",
                    "Mango",
                    "Orange",
                    "Pineapple",
                    "Strawberry",
                    "Watermelon"
                )

                ProductsScreen(itemsList, onItemClicked = { productName ->
                    backStack.add(Screen.Product(productName))
                })
            }
            entry<Screen.Product> { key ->
                ProductDetailsScreen(key.productName)
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun NavExamplePreview() {
    ExcaliburTheme {
        val previewBackStack = remember { mutableStateListOf<NavKey>(Screen.Home) }

        NavExample(previewBackStack, Modifier.padding(4.dp))
    }
}