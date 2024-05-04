package com.example.shhsactivities.ui.components.general

import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shhsactivities.data.models.Announcement
import com.example.shhsactivities.ui.screens.components.AnnouncementItem
import com.example.shhsactivities.ui.theme.Typography
import java.util.Date

@Composable
fun DropDownMenu(
    isExpanded: Boolean,
    onDropDownArrowClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    title: String = "",
    content: @Composable() (ColumnScope.() -> Unit)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = Typography.headlineSmall,
                modifier = Modifier
                    .padding(4.dp)
                    .weight(0.85f)
            )

            AnimatedContent(
                targetState = isExpanded, label = "Drop Down Arrow"
            ) { isExpanded ->
                IconButton(onClick = { onDropDownArrowClick() }) {
                    Icon(
                        imageVector = if(isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if(isExpanded) Icons.Default.KeyboardArrowUp.name else Icons.Default.KeyboardArrowDown.name,
                        tint = Color.Black,
                        modifier = Modifier
                            .scale(1.5f)
                            .weight(0.15f)
                    )
                }
            }
        }
        /*
        TODO FIX ANIMATION
         */

        AnimatedVisibility(
            visible = isExpanded, label = "Drop Down Menu",
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it })
        ) {
            if(isExpanded) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color)) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp)
                            .background(Color.LightGray, RoundedCornerShape(15.dp))
                            .clip(RoundedCornerShape(15.dp))
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewDropDownMenu() {
    var isExpanded by remember { mutableStateOf(false) }
    
    DropDownMenu(
        isExpanded = isExpanded,
        onDropDownArrowClick = {isExpanded = !isExpanded},
        title = "Student Government and Politics Club"
    ) {
        AnnouncementItem(
            announcement = Announcement(
                date = Date(),
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam finibus ullamcorper tortor in imperdiet. Etiam et cursus justo. Fusce eros est, finibus vitae nisi non, pharetra tristique felis. Phasellus congue ex sed risus efficitur, nec hendrerit lorem elementum. Etiam eget aliquam leo. Aenean sed bibendum nulla. Morbi in consectetur est. Pellentesque eget diam non libero vehicula molestie. Maecenas finibus, ligula ut ultrices ornare, massa velit maximus massa, a mollis eros mi id justo. Pellentesque vitae bibendum felis. Fusce mi arcu, laoreet eget sodales cursus, fermentum in risus. Fusce odio enim, tincidunt at enim id, tincidunt porta libero. Etiam venenatis dignissim lorem non convallis. Morbi dictum augue quis massa dignissim, a dignissim diam convallis.",
            ),
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp),
            onClickAnnouncement = {}
        )
    }
}