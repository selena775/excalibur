package navigation.form

import android.R.attr.tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// 1. ViewModel to manage the state of the form
class FormViewModel : ViewModel() {

    // List of all available tags
    private val _availableTags = MutableStateFlow(
        listOf("Name", "Email", "Phone", "Address", "Notes", "Occupation", "Company")
    )
    val availableTags = _availableTags.asStateFlow()

    // Set of currently selected tags
    private val _selectedTags = MutableStateFlow(setOf<String>("Email", "Phone", "Address", "Notes"))
    val selectedTags = _selectedTags.asStateFlow()

    // Map to store the text content for each dynamically generated field
    // Key: Tag name (String), Value: Text content (String)
    private val _fieldStates = MutableStateFlow(mutableMapOf<String, String>(
        "Email" to "selena@gmail.com",
        "Name" to "Selena"))
    val fieldStates = _fieldStates.asStateFlow()

    /**
     * Toggles the selection of a tag.
     * If selected, adds it to the selectedTags set and initializes its field state if not present.
     * If deselected, removes it from the selectedTags set and clears its field state.
     */
    fun toggleTagSelection(tag: String) {
        _selectedTags.update { currentTags ->
            if (currentTags.contains(tag)) {
                // If tag is deselected, remove its state
                _fieldStates.update { currentFieldStates ->
                    currentFieldStates.remove(tag)
                    currentFieldStates // Return the modified map
                }
                currentTags - tag // Remove tag from selected set
            } else {
                // If tag is selected, add it and initialize its state if not already there
                _fieldStates.update { currentFieldStates ->
                    if (!currentFieldStates.containsKey(tag)) {
                        currentFieldStates[tag] = "" // Initialize with empty string
                    }
                    currentFieldStates // Return the modified map
                }
                currentTags + tag // Add tag to selected set
            }
        }
    }

    /**
     * Updates the text content for a specific field.
     */
    fun updateSelectedTags( newText: String) {
        _selectedTags.update {
            newText.split(",").toSet()
        }
    }
    /**
     * Updates the text content for a specific field.
     */
    fun updateFieldText(tag: String, newText: String) {
        _fieldStates.update { currentMap ->
            currentMap.toMutableMap().apply {
                this[tag] = newText
            }
        }
    }
}

