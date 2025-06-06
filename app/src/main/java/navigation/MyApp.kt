package navigation

import HomeScreen
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import navigation.Screen.Product
import com.example.excalibur.ui.theme.ExcaliburTheme
import navigation.products.ProductsScreen


@Composable
fun MyApp() {
    // Create a back stack, specifying the key the app should start with
    val backStack = rememberSaveable { mutableStateListOf<NavKey>(Screen.Home) }
    val screensToShow = listOf(Screen.Home, Screen.Products, Product("None"))

    // Your app's theme
    ExcaliburTheme {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                screensToShow.forEach {
                    item(
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = it.name
                            )
                        },
                        label = { Text(it.name) },
                        selected = it.javaClass == backStack.last().javaClass,  // should be it == backStack.last() if it is bottom bar
                        onClick = { backStack.add(it) }
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
