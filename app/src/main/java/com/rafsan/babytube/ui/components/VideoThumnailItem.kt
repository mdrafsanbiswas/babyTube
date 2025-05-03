package com.rafsan.babytube.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rafsan.babytube.data.VideoItem

@Composable
fun ThumbnailItem(data: VideoItem, isSelected: Boolean, onItemSelected: () -> Unit, columns: Int) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / columns
    val itemHeight = itemWidth * 9 / 16

    AsyncImage(
        model = data.thumbnail,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(
                border = if (isSelected)
                    BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
                else
                    BorderStroke(2.dp, Color.Gray.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(8.dp)
            )

            .clickable {
                onItemSelected()
            }

    )

}