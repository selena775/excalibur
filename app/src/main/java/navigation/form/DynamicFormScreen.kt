package navigation.form

import android.R.attr.tag
import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.excalibur.ui.theme.ExcaliburTheme



// 3. Composable for the entire dynamic form screen
@OptIn(ExperimentalMaterial3Api::class) // Required for OutlinedTextField in Material 3
@Composable
fun DynamicFormScreen(viewModel: FormViewModel = viewModel()) {
    val availableTags by viewModel.availableTags.collectAsState()
    val selectedTags by viewModel.selectedTags.collectAsState()
    val fieldStates by viewModel.fieldStates.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.statusBars)
    ) {
        item {
            TextField(
                value = selectedTags.joinToString (separator = ","),
                onValueChange = { newValue -> viewModel.updateSelectedTags(newValue) }
            )
        }
        // Iterate over ALL available tags, but only show the field if the tag is selected
        items(availableTags.sorted()) { tag -> // <<< Changed from selectedTags to availableTags
            // Use animateContentSize for smooth appearance/disappearance

//            // Use AnimatedVisibility for the content that should slide in/out
//            AnimatedVisibility(
//                visible = selectedTags.contains(tag),
//                enter = expandVertically(
//                    animationSpec = tween(durationMillis = 1000)
//                ) { fullHeight -> // Slide in from top
//                    -fullHeight
//                } + fadeIn(animationSpec = tween(durationMillis = 1000)),
//                exit = shrinkVertically (
//                    animationSpec = tween(durationMillis = 1000)
//                ) { fullHeight -> // Slide out to top
//                    -fullHeight
//                } + fadeOut(animationSpec = tween(durationMillis = 1000))
//            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .animateContentSize(animationSpec = tween(
                            durationMillis = 5000))
                ) { // <<< Added Column with animateContentSize
                    if (selectedTags.contains(tag)) { // <<< Conditional rendering based on selection
                        OutlinedTextField(
                            label = { Text(tag) },
                            value = fieldStates[tag] ?: "",
                            onValueChange = { newValue -> viewModel.updateFieldText(tag, newValue) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
//            }
        }
    }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExcaliburTheme {
        DynamicFormScreen()
    }
}