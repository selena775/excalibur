package navigation.email

import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.rememberPaneExpansionState
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.excalibur.ui.theme.ExcaliburTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun EmailListDetailApp(viewModel: EmailViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    // The key for the selected item in the navigator
    // We use selectedEmailId for simplicity, but could be the Email object itself
    val navigator = rememberListDetailPaneScaffoldNavigator()
    val paneExpansionState  = rememberPaneExpansionState(navigator.scaffoldValue)

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                EmailListPane(
                    emails = uiState.emails,
                    selectedEmailId = uiState.selectedEmailId,
                    onEmailClick = { emailId ->
                        viewModel.selectEmail(emailId)
                        scope.launch {
                            // Navigate to the detail pane with the selected email ID
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, emailId)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                val selectedEmail = uiState.selectedEmail
                EmailDetailPane(email = selectedEmail)
            }
        },
        paneExpansionState = paneExpansionState,
        // CUSTOM DRAG HANDLE IMPLEMENTATION
        paneExpansionDragHandle = {
            val interactionSource = remember { MutableInteractionSource() }
            // Apply the paneExpansionDraggable modifier to your custom composable
            // Ensure a sufficient hit area for the drag.
            Box(
                modifier = Modifier
                    .width(16.dp) // Width for vertical handle
                    .fillMaxHeight()
                    .paneExpansionDraggable(
                        paneExpansionState, // Use the paneExpansionState
                        // Use a reasonable size for the draggable area, e.g., 48.dp is common for touch targets.
                        48.dp, // Or LocalMinimumInteractiveComponentSize.current
                        interactionSource
                    )
                    .background(MaterialTheme.colorScheme.surfaceVariant) // Make it visible
                    .padding(horizontal = 4.dp), // Some padding for appearance
                contentAlignment = Alignment.Center
            ) {
                // You can add a subtle visual indicator like a small line
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(32.dp)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                )
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 720, heightDp = 400) // Large screen preview
@Preview(showBackground = true, widthDp = 360, heightDp = 640) // Small screen preview
@Composable
fun EmailListDetailAppPreview() {
    ExcaliburTheme {
        EmailListDetailApp()
    }
}