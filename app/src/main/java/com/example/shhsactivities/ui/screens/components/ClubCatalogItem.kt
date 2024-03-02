package com.example.shhsactivities.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.ui.theme.Typography

@Composable
fun ClubCatalogItem(
    club: Club,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit
) {
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(25))) {
        Row (modifier = Modifier
            .background(Color.White)
            .then(modifier)
        ){
            Column (
                modifier = Modifier
                    .weight(0.75f)
                    .padding(horizontal = 8.dp),
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
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewClubCatalogWidget() {
    ClubCatalogItem(
        club = Club(
            name = "Bruh Club",
            imageUrl = "https://lh3.googleusercontent.com/usAj44Gr-NlCTP3mtz8ia2VDYcQE3LHbYpchNMogeWmPxNclarBl3skO5plQWbHmEmKzEPKDexf2Kop2ME8L4edc=s274",
            room = "314",
            meetingFrequency = "Every Friday",
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {}
    )
}