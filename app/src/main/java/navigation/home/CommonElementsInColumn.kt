package navigation.home

import DraggableBottomPopupScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


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
    Row {
        Column(Modifier.weight(1f)) {
            Text(text = "Drag the red box horizontally!")
            HorizonatlyDraggableBox()
        }
        Column(Modifier.weight(1f)) {
            Text(text = "Drag the blue box in any direction!")
            DraggableBoxAnyDirectionExample()
        }
    }
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