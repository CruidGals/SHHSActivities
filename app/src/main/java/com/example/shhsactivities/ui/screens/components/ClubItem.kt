package com.example.shhsactivities.ui.screens.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun ClubItem(
    club: Club,
    modifier: Modifier = Modifier,
    onClick: (club: Club) -> Unit
) {
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(25))
        .clickable {
            scope.launch {
                onClick(club)
            }
        }
    ) {
        Row (modifier = Modifier
            .background(club.category.color)
            .then(modifier)
        ){
            Column (
                modifier = Modifier
                    .weight(0.75f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = club.name,
                    style = Typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Room ${club.room}",
                    fontStyle = FontStyle.Italic,
                    style = Typography.labelMedium
                )

                Text(
                    text = "Meetings: ${club.meetingFrequency}",
                    fontStyle = FontStyle.Italic,
                    style = Typography.labelMedium
                )
            }

            Column(
                modifier = Modifier.weight(.25f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = club.imageUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

@Composable
fun MiniClubItem(
    club: Club,
    modifier: Modifier = Modifier,
    onClick: (club: Club) -> Unit
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                scope.launch {
                    onClick(club)
                }
            }
    ) {
        Row (modifier = Modifier
            .width(200.dp)
            .height(100.dp)
            .background(club.category.color)
            .then(modifier)
        ){
            Column (
                modifier = Modifier
                    .weight(0.70f)
                    .padding(start = 8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                var textStyle by remember { mutableStateOf(Typography.headlineSmall) }
                var readyToDraw by remember { mutableStateOf(false) }

                Text(
                    text = club.name,
                    style = textStyle,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    softWrap = false,
                    modifier = modifier.drawWithContent {
                        if (readyToDraw) drawContent()
                    },
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.didOverflowWidth) {
                            textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.9)
                        } else {
                            readyToDraw = true
                        }
                    }
                )
                Text(
                    text = "Room ${club.room}",
                    fontStyle = FontStyle.Italic,
                    style = Typography.labelMedium
                )
            }

            Column(
                modifier = Modifier.fillMaxSize().weight(.30f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = club.imageUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewMiniClub() {
    MiniClubItem(
        club = Club(
            name = "Bruh Club",
            imageUrl = "https://lh3.googleusercontent.com/usAj44Gr-NlCTP3mtz8ia2VDYcQE3LHbYpchNMogeWmPxNclarBl3skO5plQWbHmEmKzEPKDexf2Kop2ME8L4edc=s274",
            room = "314",
            meetingFrequency = "Every Friday",
        ),
        modifier = Modifier
            .padding(8.dp),
        onClick = {}
    )
}