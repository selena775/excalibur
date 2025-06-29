package navigation.home.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SectionedLazyColumn(
    sections: List<Section>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        state = state
    ) {
        sections.forEach { section ->
            // Sticky Header for the current section
            stickyHeader {
                SectionHeader(section.headerTitle)
            }

            // Items for the current section
            items(section.items) { item ->
                SectionItem(item)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer) // Use a distinct background
            .padding(16.dp),
        shadowElevation = 4.dp // Optional: Add some shadow elevation
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun SectionItem(itemText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(Color.White) // Just for visual distinction
    ) {
        Text(text = itemText, style = MaterialTheme.typography.bodyMedium)
    }
}

//// Example usage:
//@Composable
//fun MyScreen() {
//
//    Column {
//        Text(
//            text = "My Day Planner",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(16.dp)
//        )
//        SectionedLazyColumn()
//    }
//}