import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign

@Composable
fun VideoScreenList(
    items: List<String>,
    columns: Int = 3,
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }
    var selectedItem by remember { mutableStateOf("") }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / columns
    val itemHeight = itemWidth * 9 / 16

    val gridState = rememberLazyGridState()


    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(columns),
        modifier = modifier
            .onPreviewKeyEvent {
                if (it.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false

                when (it.key) {
                    Key.DirectionRight -> {
                        if ((selectedIndex + 1) % columns != 0 && selectedIndex + 1 < items.size) {
                            selectedIndex++
                        }
                        Log.d("check_index_right", "$selectedIndex, $columns, ${items.size}")
                        true
                    }

                    Key.DirectionLeft -> {
                        if (selectedIndex % columns != 0) {
                            selectedIndex--
                        }
                        Log.d("check_index_left", "$selectedIndex, $columns")
                        true
                    }

                    Key.DirectionDown -> {
                        val next = selectedIndex + columns
                        if (next < items.size) selectedIndex = next
                        Log.d("check_index_down", "$selectedIndex, $next")
                        true
                    }

                    Key.DirectionUp -> {
                        val prev = selectedIndex - columns
                        if (prev >= 0) selectedIndex = prev
                        Log.d("check_index_up", "$selectedIndex, $prev")
                        true
                    }

                    Key.Enter -> {
                        selectedItem = items[selectedIndex]
                        onItemSelected(selectedItem)
                        Log.d("check_index_enter", "$selectedItem, $selectedIndex")
                        true
                    }


                    else -> false
                }
            }
            .focusable(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp)
    ) {
        itemsIndexed(items) { index, item ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(itemWidth - 12.dp)
                    .height(itemHeight)
                    .border(
                        border = if (index == selectedIndex)
                            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                        else
                            BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        selectedIndex = index
                        onItemSelected(item)
                    }
                    .padding(4.dp)
            ) {
                Text(
                    text = item,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    LaunchedEffect(selectedIndex) {
        gridState.animateScrollToItem(selectedIndex)
        onItemSelected(items[selectedIndex])
    }
}
