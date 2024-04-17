package com.example.shhsactivities.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun PopupBox(
    width: Float,
    height: Float,
    isShown: Boolean,
    onOutsideClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
) {
    if (isShown) {
        Box(
            modifier = modifier
        ) {
            Popup(
                alignment = Alignment.Center,
                properties = PopupProperties(
                    excludeFromSystemGesture = true
                ),
                onDismissRequest = { onOutsideClick() }
            ) {
                Box(
                    modifier = Modifier
                        .width(width.dp)
                        .height(height.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .border(3.dp, Color.DarkGray, RoundedCornerShape(15.dp))
                        .background(Color.White, RoundedCornerShape(15.dp))
                ) {
                    content()
                }
            }
        }
    }
}