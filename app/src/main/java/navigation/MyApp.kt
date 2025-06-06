package navigation

import HomeScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.excalibur.OrderDetailsScreen
import navigation.Screen.Product
import com.example.excalibur.ui.theme.ExcaliburTheme
import navigation.orders.OrdersScreen
import navigation.products.ProductsScreen


@Composable
fun MyApp() {
    // Create a back stack, specifying the key the app should start with
    val backStack = rememberSaveable { mutableStateListOf<NavKey>(Screen.Home) }

    val screensToShow = listOf(
        ScreenTab(Screen.Home, Icons.Default.Home),
        ScreenTab(Screen.Products, Icons.Default.ShoppingCart),
        ScreenTab(Screen.Orders, Icons.Default.ShoppingCart))

    val selectedTab by remember {
        derivedStateOf {
            backStack.findLast { navKey ->
                screensToShow.map { it.screen }.contains(navKey)
            }
        }
    }


    // Your app's theme
    ExcaliburTheme {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                screensToShow.forEach {screenTab ->
                    item(
                        icon = {
                            Icon(
                                screenTab.icon,
                                contentDescription = screenTab.screen.name
                            )
                        },
                        label = { Text(screenTab.screen.name) },
                        selected = screenTab.screen == selectedTab,
                        onClick = { backStack.add(screenTab.screen) }
                    )
                }
            },
        ) {
            NavDisplay(
                entryDecorators = listOf(
                    rememberSceneSetupNavEntryDecorator(),
                    rememberSavedStateNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<Screen.Home> { key ->
                        HomeScreen("Welcome to Nav3", onNextScreen = {
                            backStack.add(Screen.Products)
                        })
                    }
                    entry<Screen.Products> { key ->
                        ProductsScreen(onItemClicked = { productName ->
                            backStack.add(Product(productName))
                        })
                    }
                    entry<Product> { key ->
                        ProductDetailsScreen(key.productName)
                    }
                    entry<Screen.Orders> { key ->
                        OrdersScreen(onItemClicked = { orderName ->
                            backStack.add(Screen.Order(orderName))
                        })
                    }
                    entry<Screen.Order> { key ->
                        OrderDetailsScreen(key.orderName)
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NavExamplePreview() {
    MyApp()
}
