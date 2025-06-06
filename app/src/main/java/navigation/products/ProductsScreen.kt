package navigation.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit
) {
    val list by viewModel.productsUiState.collectAsStateWithLifecycle()
    ProductsScreen(list, modifier, onItemClicked)
}

@Composable
fun ProductsScreen(
    itemsList: List<String>,
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit
) {
    LazyColumn {
        items(itemsList) { item ->
            Text(
                text = item,
                modifier = Modifier.clickable {
                    // Action to perform when the item is clicked
                    onItemClicked(item)
                }
            )
        }
    }
}