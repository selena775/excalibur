package navigation.home

import DraggableBottomPopupScreen
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// Key: 'ColumnScope' as a receiver
@Composable
fun ColumnScope.CommonElementsInColumn(
    greeting: String,
    modifier: Modifier = Modifier,
    onNextScreen: () -> Unit
) {
// These items are directly part of the parent Column's layout

    Text(text = greeting)

    Button(onClick = onNextScreen) {
        Text("On Next Screen")
    }
    DraggableBottomPopupScreen(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .weight(0.3f)
    )

}


@Composable
fun CollapsingToolbarButton(isVisible: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick) {
        if (isVisible) {
            Text(" Hide CollapsingToolbarScreen")
        } else {
            Text(" Show CollapsingToolbarScreen")
        }
    }
}