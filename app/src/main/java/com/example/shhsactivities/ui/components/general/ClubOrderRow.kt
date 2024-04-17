package com.example.shhsactivities.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shhsactivities.data.models.ClubCategory
import com.example.shhsactivities.ui.components.OrderType
import com.example.shhsactivities.ui.components.OrderDirection
import com.example.shhsactivities.ui.theme.Typography

@Composable
fun ClubOrderRow(
    order: OrderType,
    onTitleFilterClick: () -> Unit,
    onCategoryClick: (ClubCategory) -> Unit,
    modifier: Modifier = Modifier,
    showLeadershipFilter: Boolean = false, //For club catalog menu
    onLeadershipFilterClick: () -> Unit = {},
) {
    var categoryPopup by remember { mutableStateOf(false) }

    Box {
        LazyRow(
            modifier = modifier
        ) {
            item {
                Row(modifier = Modifier.padding(horizontal = 4.dp)) {
                    ClubOrderButton(
                        modifier = Modifier
                            .background(if (order is OrderType.Title) Color.DarkGray else Color.White)
                            .padding(horizontal = 6.dp)
                            .clickable {
                                onTitleFilterClick()
                            }
                    ) {
                        Text(
                            text = "Title" + if (order is OrderType.Title) "  ${order.direction}" else "",
                            color = if (order is OrderType.Title) Color.White else Color.Black
                        )
                    }
                }
            }

            if (showLeadershipFilter) {
                item {
                    Row(modifier = Modifier.padding(horizontal = 4.dp)) {
                        ClubOrderButton(
                            modifier = Modifier
                                .background(if (order is OrderType.Leadership) Color.DarkGray else Color.White)
                                .padding(horizontal = 6.dp)
                                .clickable {
                                    onLeadershipFilterClick()
                                }
                        ) {
                            Text(
                                text = "Leadership" + if (order is OrderType.Leadership) "  ${order.direction}" else "",
                                color = if (order is OrderType.Leadership) Color.White else Color.Black
                            )
                        }
                    }
                }
            }

            item {
                Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                    ClubOrderButton(
                        modifier = Modifier
                            .background(if (order.categories.isNotEmpty()) Color.DarkGray else Color.White)
                            .padding(horizontal = 6.dp)
                            .clickable {
                                categoryPopup = true
                            }
                    ) {
                        Text(
                            text = "Categories ⋮",
                            color = if (order.categories.isNotEmpty()) Color.White else Color.Black
                        )
                    }
                }
            }
        }

        PopupBox(
            width = 300f,
            height = 400f,
            isShown = categoryPopup,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            onOutsideClick = { categoryPopup = false })
        {
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (category in ClubCategory.entries) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f).padding(horizontal = 5.dp),
                            text = category.title,
                            color = category.color,
                            textAlign = TextAlign.Right,
                            style = Typography.labelLarge
                        )
                        RadioButton(selected = category in order.categories, onClick = { onCategoryClick(category) })
                    }
                }
            }
        }
    }
}

@Composable
fun ClubOrderButton(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(Dp.Hairline, Color.DarkGray, RoundedCornerShape(10.dp))
            .then(modifier)
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewClubOrderRow() {
    ClubOrderRow(
        order = OrderType.Title(OrderDirection.Ascending),
        onTitleFilterClick = { /*TODO*/ },
        onLeadershipFilterClick = { /*TODO*/ },
        onCategoryClick = {},
        modifier = Modifier.fillMaxWidth(),
        showLeadershipFilter = true
    )
}