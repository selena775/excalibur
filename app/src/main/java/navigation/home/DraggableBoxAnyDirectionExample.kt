package navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun DraggableBoxAnyDirectionExample() {


    // State to hold the current x and y offsets of the box
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) } // Apply both offsets
            .background(Color.Blue)
            .size(100.dp)
            .pointerInput(Unit) { // Use pointerInput for more granular gesture control
                detectDragGestures { change, dragAmount ->
                    change.consume() // Consume the event to prevent other gesture detectors from reacting
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        Text(
            text = "Drag Me!",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDraggableBoxAnyDirectionExample() {
    DraggableBoxAnyDirectionExample()
}