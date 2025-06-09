package navigation.email

import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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