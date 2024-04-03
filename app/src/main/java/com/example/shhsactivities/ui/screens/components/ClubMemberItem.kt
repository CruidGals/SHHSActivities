package com.example.shhsactivities.ui.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.ui.theme.Typography

@Composable
fun ClubMemberItem(
    user: UserData,
    onClickContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = user.profilePictureUrl,
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = user.username ?: "",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = Typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { onClickContact() }) {
                Icon(
                    Icons.Default.Email,
                    Icons.Default.Email.name
                )
            }
        }
    }
}