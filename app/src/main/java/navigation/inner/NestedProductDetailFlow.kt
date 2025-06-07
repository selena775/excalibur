package navigation.inner

import HomeScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import navigation.Screen

@Composable
fun NestedProductDetailFlow(
    productId: String,
    onNavigateOutside: (Screen) -> Unit // Optional: if nested flow can influence outer navigation
) {
    // This state holder manages the internal tabs of the product detail InnerScreen
    // Create a back stack, specifying the key the app should start with
    val backStack =
        rememberSaveable { mutableStateListOf<NavKey>(InnerScreen.ProductDetailTabOverview(productId)) }
    val selectedTabIndex by remember {
        derivedStateOf {
            when (backStack.last()) {
                is InnerScreen.ProductDetailTabOverview -> 0
                is InnerScreen.ProductDetailTabReviews -> 1
                is InnerScreen.ProductDetailTabSpecs -> 2
                else -> 0
            }
        }
    }
    val tabs = remember(productId) { // Remember the tabs, re-create if productId changes
        listOf(
            TabItem("Overview", InnerScreen.ProductDetailTabOverview(productId)),
            TabItem("Reviews", InnerScreen.ProductDetailTabReviews(productId)),
            TabItem("Specs", InnerScreen.ProductDetailTabSpecs(productId))
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Product Details for: $productId", style = MaterialTheme.typography.headlineMedium)
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {


            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == selectedTabIndex, // Use the single source of truth
                    onClick = { backStack.add(tabItem.innerScreen) },
                    text = { Text(tabItem.title) }
                )
            }
        }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<InnerScreen.ProductDetailTabOverview> { key ->
                    ProductOverviewInnerScreen(productId)
                }
                entry<InnerScreen.ProductDetailTabReviews> { key ->
                    ProductReviewsInnerScreen(productId)
                }
                entry<InnerScreen.ProductDetailTabSpecs> { key ->
                    ProductSpecsInnerScreen(productId)
                }
            }
        )

        // Example of how a nested flow might navigate "up" or "out" if needed
        Button(onClick = { onNavigateOutside(Screen.Orders) }) {
            Text("Go to Orders (from Nested)")
        }

        // Example of how a nested flow might navigate "up" or "out" if needed
        Button(onClick = { backStack.removeLastOrNull() }) {
            Text("Back in this nested child")
        }
    }
}

// Dummy InnerScreens for product detail tabs
@Composable
fun ProductOverviewInnerScreen(productId: String) {
    Text("Overview of $productId")
}

@Composable
fun ProductReviewsInnerScreen(productId: String) {
    Text("Reviews for $productId")
}

@Composable
fun ProductSpecsInnerScreen(productId: String) {
    Text("Specs for $productId")
}