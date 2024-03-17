package com.example.shhsactivities.ui.viewmodels.components.general

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryTextChange: (String) -> Unit,
    placeHolderText: String,
    onCanceledClick: () -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = remember { FocusRequester() },
    enabled: Boolean = true
) {
    var showCancelButton by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val interactionSource = remember { MutableInteractionSource() }

        BasicTextField(
            value = query,
            onValueChange = onQueryTextChange,
            enabled = enabled,
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .onFocusChanged {
                    showCancelButton = it.isFocused
                },
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
            singleLine = true,
            interactionSource = interactionSource
        ) { innerTextField ->
            Surface(
                shape = RoundedCornerShape(16.dp)
            ) {
                TextFieldDefaults.DecorationBox(
                    value = query,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(0.dp),
                    placeholder = {
                        Text(
                            text = placeHolderText,
                            color = Color.DarkGray
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = Icons.Default.Search.name
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = showCancelButton,
                            enter = scaleIn(),
                            exit = ExitTransition.None,
                            label = "cancel_button"
                        ) {
                            IconButton(onClick = {
                                onCanceledClick()
                                focusManager.clearFocus()
                            } ) {
                                Icon(
                                    Icons.Default.Close,
                                    Icons.Default.Close.name
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.Gray,
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(query = "", onQueryTextChange = {}, placeHolderText = "Search", onCanceledClick = {})
}