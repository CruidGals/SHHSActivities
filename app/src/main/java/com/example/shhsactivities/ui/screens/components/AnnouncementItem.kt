package com.example.shhsactivities.ui.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shhsactivities.data.models.Announcement
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.ui.theme.Typography
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnnouncementItem(
    announcement: Announcement,
    modifier: Modifier = Modifier,
    onClickAnnouncement: (Announcement) -> Unit
) {
    val user = announcement.user?.get()?.result?.toObject<UserData>() ?: UserData(
        uid = "Blah Blah Blah",
        username = "CruidGals",
        profilePictureUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/USMC-06868.jpg/1280px-USMC-06868.jpg"
    )

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(25))
            .clickable {
                scope.launch {
                    onClickAnnouncement(announcement)
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val formatter = SimpleDateFormat("hh:mm a  MM/dd/yy", Locale.getDefault())

                Row(verticalAlignment = Alignment.CenterVertically) {
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
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = Typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = formatter.format(announcement.date),
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = Typography.titleMedium
                )
            }

            Text(
                text = announcement.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = Typography.bodyMedium
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewAnnouncementItem() {
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