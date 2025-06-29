package navigation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import navigation.home.section.SectionedLazyColumn
import navigation.home.section.myDataSections
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarScreen(modifier: Modifier) {

    val toolbarHeight = 56.dp  // Standard AppBar height
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    // This state will track the current vertical offset of the toolbar
    // It will be negative when the toolbar is collapsing
    // Initial offset is 0f, meaning it starts at the top of its *parent's* content area
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

    val lazyListState = rememberLazyListState()
    val isFirstItemVisible by remember {
        derivedStateOf {
            // firstVisibleItemIndex will be 0 if the first item is at the very top (or partially visible)
            lazyListState.firstVisibleItemIndex == 0
        }
    }


    // Our NestedScrollConnection implementation
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y

                if(!isFirstItemVisible) {
                    return Offset.Zero
                }

                // The valid range for the toolbar's offset is now simply
                // from 0f (fully visible) to -toolbarHeightPx (fully collapsed)
                val minOffset = -toolbarHeightPx // Fully collapsed
                val maxOffset = 0f // Fully visible

                val consumedByToolbar = (toolbarOffsetHeightPx.value + delta).coerceIn(
                    minOffset,
                    maxOffset
                ) - toolbarOffsetHeightPx.value

                toolbarOffsetHeightPx.value += consumedByToolbar

                return Offset(0f, consumedByToolbar)
            }
        }
    }

    Surface (
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .nestedScroll(nestedScrollConnection),
        color = MaterialTheme.colorScheme.background
    ) {
        TopAppBar(
            title = {
                Column {
                    Text("Collapsing $isFirstItemVisible")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarHeight)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
        )

        SectionedLazyColumn(
            sections = myDataSections,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = toolbarHeight,
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            ),
            state = lazyListState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCollapsingToolbarScreenEdgeToEdge() {
    CollapsingToolbarScreen(modifier = Modifier)
}