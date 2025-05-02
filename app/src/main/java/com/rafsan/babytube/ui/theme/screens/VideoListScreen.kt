import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import com.rafsan.babytube.ui.theme.screens.VideoThumbnailItem

@Composable
fun VideoScreenList(
    items: List<String>,
    columns: Int = 3,
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedItem by remember { mutableStateOf("") }

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
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        itemsIndexed(items) { index, item ->

            VideoThumbnailItem(
                data = item,
                isSelected = index == selectedIndex,
                columns = columns,
                onItemSelected = {
                    selectedIndex = index
                    onItemSelected(item)
                }
            )
        }
    }

    LaunchedEffect(selectedIndex) {
        gridState.animateScrollToItem(selectedIndex)
        onItemSelected(items[selectedIndex])
    }
}
