package navigation.email

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EmailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EmailUiState())
    val uiState: StateFlow<EmailUiState> = _uiState.asStateFlow()

    init {
        // Simulate loading emails
        _uiState.update { it.copy(emails = sampleEmails) }
    }

    fun selectEmail(emailId: Int?) {
        _uiState.update { currentState ->
            currentState.copy(selectedEmailId = emailId)
        }
    }
}

data class EmailUiState(
    val emails: List<Email> = emptyList(),
    val selectedEmailId: Int? = null
) {
    val selectedEmail: Email?
        get() = emails.find { it.id == selectedEmailId }
}