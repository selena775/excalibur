package navigation.orders

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
fun OrdersScreen(
    viewModel: OrdersViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit
) {
    val list by viewModel.ordersUiState.collectAsStateWithLifecycle()
    OrdersScreen(list, modifier, onItemClicked)
}

@Composable
fun OrdersScreen(
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