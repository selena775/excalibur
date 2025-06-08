package navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.Scene
import androidx.navigation3.ui.SceneStrategy

data class SinglePaneScene<T : Any>(
    override val key: T,
    val entry: NavEntry<T>,
    override val previousEntries: List<NavEntry<T>>,
) : Scene<T> {
    override val entries: List<NavEntry<T>> = listOf(entry)
    override val content: @Composable () -> Unit = { entry.content.invoke(entry.key) }
}

/**
 * A [SceneStrategy] that always creates a 1-entry [Scene] simply displaying the last entry in the
 * list.
 */
public class SinglePaneSceneStrategy<T : Any> : SceneStrategy<T> {
    @Composable
    override fun calculateScene(entries: List<NavEntry<T>>, onBack: (Int) -> Unit): Scene<T> =
        SinglePaneScene(
            key = entries.last().key,
            entry = entries.last(),
            previousEntries = entries.dropLast(1)
        )
}