package navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface Screen : NavKey, Parcelable {
    @Parcelize
    data object Home : Screen

    @Parcelize
    data object Products : Screen

    @Parcelize
    data class Product(val productName: String) : Screen

    @Parcelize
    data object Orders : Screen

    @Parcelize
    data class Order(val orderName: String) : Screen

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