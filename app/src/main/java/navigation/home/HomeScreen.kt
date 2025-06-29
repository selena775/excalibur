import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import kotlinx.coroutines.launch
import navigation.home.CollapsingToolbarButton
import navigation.home.CollapsingToolbarScreen
import navigation.home.CommonElementsInColumn

@Composable
fun HomeScreen(
    greeting: String,
    modifier: Modifier = Modifier,
    onNextScreen: () -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    var isWide by remember {
        mutableStateOf(
            with(windowSizeClass) {
                windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)
            }
        )
    }

    var collapsibleToolbarVisible by remember { mutableStateOf(false) }

    if (isWide) {
        Row {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between children
            ) {
                CommonElementsInColumn(greeting, modifier, onNextScreen)
                CollapsingToolbarButton(collapsibleToolbarVisible) {
                    collapsibleToolbarVisible = !collapsibleToolbarVisible
                }
            }

            if (collapsibleToolbarVisible) {
                CollapsingToolbarScreen(modifier = Modifier.weight(2f)) // Takes 1 part of remaining space)
            }
        }
    } else {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between children
        ) {

            CommonElementsInColumn(greeting, modifier, onNextScreen)
            CollapsingToolbarButton(collapsibleToolbarVisible) {
                collapsibleToolbarVisible = !collapsibleToolbarVisible
            }

            if (collapsibleToolbarVisible) {
                CollapsingToolbarScreen(modifier = Modifier.weight(0.7f)) // Takes 1 part of remaining space)
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraggableBottomPopupScreen(modifier: Modifier) {
    // State to control whether the bottom sheet is shown
    var showBottomSheet by remember { mutableStateOf(false) }

    // State for the bottom sheet itself, allows controlling its expanded/hidden state
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false // Set to false to enable the partially expanded state
    )

    // Coroutine scope to launch suspend functions for sheet control
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { showBottomSheet = true }) {
            Text("Show Draggable Bottom Sheet")
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                // Dismiss the sheet when the user taps outside or drags it down
                showBottomSheet = false
            },
            sheetState = sheetState,
            // Optional: Customize properties like shape, background color, etc.
            // sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            // sheetContainerColor = Color.LightGray
        ) {
            // Content of your bottom sheet
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "This is your draggable bottom sheet content!",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "You can drag me up and down.",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                repeat(20) {
                    Text(text = "Item $it")
                }
                Spacer(modifier = Modifier.height(200.dp)) // Add some height to allow for dragging
                Button(onClick = {
                    // Programmatically hide the sheet
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        // Once the animation is complete, update the state to truly dismiss it
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("Hide Sheet")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDraggableBottomPopupScreen() {
    DraggableBottomPopupScreen(modifier = Modifier.fillMaxWidth())
}