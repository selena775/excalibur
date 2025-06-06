package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Screen : NavKey {

    @Serializable
    data object Home : Screen()

    @Serializable
    data object Products : Screen()

    @Serializable
    data class Product(val productName: String) : Screen()

    @Serializable
    data object Orders : Screen()

    @Serializable
    data class Order(val orderName: String) : Screen()

    // A simple property to get a displayable name
    val name: String
        get() = when (this) {
            Home -> "Home"
            Products -> "Products"
            Orders -> "Orders"
            is Product -> "Product $productName"
            is Order -> "Order $orderName"
        }
}

data class ScreenTab(val screen: Screen, val icon: ImageVector)