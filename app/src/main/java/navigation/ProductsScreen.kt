package navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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