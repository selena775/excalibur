package navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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

@Composable
fun HorizonatlyDraggableBox() {


// State to hold the current horizontal offset of the box
    var offsetX by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) } // Apply the offset
            .background(Color.Red)
            .size(100.dp)
            .draggable(
                orientation = Orientation.Horizontal, // Allow horizontal dragging
                state = rememberDraggableState { delta ->
                    // Update the offset when the user drags
                    offsetX += delta
                }
            )
    ) {
        Text(
            text = "Drag Me!",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}