package com.rafsan.babytube.ui.theme.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun VideoThumbnailItem(data: String, isSelected: Boolean, onItemSelected: () -> Unit, columns: Int) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / columns
    val itemHeight = itemWidth * 9 / 16

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(itemWidth - 12.dp)
            .height(itemHeight)
            .border(
                border = if (isSelected)
                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                else
                    BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onItemSelected()
            }
            .padding(4.dp)
    ) {
        Text(
            text = data,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}