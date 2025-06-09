package navigation.email

import android.os.Parcelable
import kotlinx.serialization.Serializable


@Serializable
data class Email(
    val id: Int,
    val sender: String,
    val subject: String,
    val body: String
)

val sampleEmails = listOf(
    Email(1, "Alice", "Meeting Agenda", "Hi team, here's the agenda for our meeting tomorrow..."),
    Email(2, "Bob", "Project Update", "Just wanted to give you a quick update on the project..."),
    Email(3, "Charlie", "Weekend Plans", "Are you free to grab coffee this weekend?"),
    Email(4, "David", "Important: Invoice #123", "Please find attached the invoice for your recent purchase..."),
    Email(5, "Eve", "Feedback Request", "Could you provide some feedback on the latest design draft?")
)