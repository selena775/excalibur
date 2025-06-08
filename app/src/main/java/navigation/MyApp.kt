package navigation

import HomeScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import com.example.excalibur.OrderDetailsScreen
import navigation.Screen.Product
import com.example.excalibur.ui.theme.ExcaliburTheme
import navigation.inner.NestedProductDetailFlow
import navigation.orders.OrdersScreen
import navigation.products.ProductsScreen


@Composable
fun MyApp() {
    // Create a back stack, specifying the key the app should start with
    val backStack = rememberSaveable { mutableStateListOf<NavKey>(Screen.Home) }

    val screensToShow = listOf(
        ScreenTab(Screen.Home, Icons.Default.Home),
        ScreenTab(Screen.Products, Icons.Default.ShoppingCart),
        ScreenTab(Screen.Orders, Icons.Default.ShoppingCart)
    )

    val selectedTab by remember {
        derivedStateOf {
            backStack.findLast { navKey ->
                screensToShow.map { it.screen }.contains(navKey)
            }
        }
    }


    // Your app's theme
    ExcaliburTheme {
        val adaptiveInfo = currentWindowAdaptiveInfo()
        val customNavSuiteType = with(adaptiveInfo) {
            if (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)) {
                NavigationSuiteType.NavigationRail   // TODO maybe Drawer
            } else {
                NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
            }
        }

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                screensToShow.forEach { screenTab ->
                    item(
                        icon = {
                            Icon(
                                screenTab.icon,
                                contentDescription = screenTab.screen.name
                            )
                        },
                        label = { Text(screenTab.screen.name) },
                        selected = screenTab.screen == selectedTab,
                        onClick = { backStack.navigateTo(screenTab.screen) }
                    )
                }
            },
            layoutType = customNavSuiteType
        ) {
            NavDisplay(
                entryDecorators = listOf(
                    rememberSceneSetupNavEntryDecorator(),
                    rememberSavedStateNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                backStack = backStack,
                entryProvider = entryProvider {
                    entry<Screen.Home> { key ->
                        HomeScreen("Welcome to Nav3", onNextScreen = {
                            backStack.navigateTo(Screen.Products)
                        })
                    }
                    entry<Screen.Products>(
                        // Mark this entry as eligible for two-pane display
                        metadata = mapOf(
                            TwoPaneScene.TWO_PANE_KEY to true,
                            TwoPaneScene.GROUP_TYPE to "Product Type"
                        )
                    ) { key ->
                        ProductsScreen(onItemClicked = { productName ->
                            if(backStack.last() is Product) {   // when user is in landscape mode and tap on different product
                                backStack.removeLastOrNull()
                            }
                            backStack.navigateTo(Product(productName))
                        })
                    }
                    entry<Product>(// Mark this entry as eligible for two-pane display
                        metadata = mapOf(
                            TwoPaneScene.TWO_PANE_KEY to true,
                            TwoPaneScene.GROUP_TYPE to "Product Type"
                        )
                    ) { key ->
                        NestedProductDetailFlow (key.productName){
                            backStack.navigateTo(Screen.Orders)
                        }
                    }
                    entry<Screen.Orders>(// Mark this entry as eligible for two-pane display
                        metadata = mapOf(
                            TwoPaneScene.TWO_PANE_KEY to true,
                            TwoPaneScene.GROUP_TYPE to "Order Type"
                        )
                    ) { key ->
                        OrdersScreen(onItemClicked = { orderName ->
                            if(backStack.last() is Product) {   // when user is in landscape mode and tap on different order
                                backStack.removeLastOrNull()
                            }
                            backStack.navigateTo(Screen.Order(orderName))
                        })
                    }
                    entry<Screen.Order>(// Mark this entry as eligible for two-pane display
                        metadata = mapOf(
                            TwoPaneScene.TWO_PANE_KEY to true,
                            TwoPaneScene.GROUP_TYPE to "Order Type"
                        )
                    ) { key ->
                        OrderDetailsScreen(key.orderName)
                    }
                },
                sceneStrategy = TwoPaneSceneStrategy<Any>(),
                onBack = { count ->
                    repeat(count) {
                        if (backStack.isNotEmpty()) {
                            backStack.removeLastOrNull()
                        }
                    }
                }
            )
        }
    }
}

private fun SnapshotStateList<NavKey>.navigateTo(key: NavKey) {
    add(key)
}

//private fun SnapshotStateList<NavKey>.navigateTo(key: NavKey) {
//
//    if (lastOrNull() == key) {
//        // Already at this destination, do nothing
//        return
//    }
//
//    // Check if the key already exists in the back stack
//    val existingIndex = indexOfFirst { it::class == key::class }
//
//    if (existingIndex != -1) {
//        // If it exists, remove everything *above* it to bring it to the top.
//        // This is like popUpTo(destination, {inclusive: false}, {saveState: false}) + launchSingleTop
//        // Note: This specific implementation might lose state of intermediate screens.
//        // For more advanced popUpTo behavior, you might need a more complex state holder
//        // or to use a full NavController.
//        while (size > existingIndex + 1) {
//            removeLast()
//        }
//        // If the *exact same instance* (with same arguments) is already at top, no need to add again.
//        if (lastOrNull() != key) {
//            // This handles cases where you navigate to a different instance of the same type
//            // e.g., Products -> ProductDetail(1) -> ProductDetail(2)
//            // If you want ProductDetail(1) to always be unique, this is trickier.
//            add(key)
//        }
//    } else {
//        // Destination not in back stack, add it normally
//        add(key)
//    }
//}

@Preview(showBackground = true)
@Composable
fun NavExamplePreview() {
    MyApp()
}
