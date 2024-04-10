package com.example.shhsactivities.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shhsactivities.ui.theme.Typography

@Composable
fun ErrorScreen(
    error: String = ""
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Warning,
            Icons.Default.Warning.name,
            Modifier.scale(5f)
        )

        Text(
            modifier = Modifier.padding(vertical = 40.dp),
            text = "Error $error",
            style = Typography.headlineSmall,
            color = Color.DarkGray
        )
    }
}

@Preview
@Composable
fun PreviewErrorScreen() {
    ErrorScreen("loading clubs")
}