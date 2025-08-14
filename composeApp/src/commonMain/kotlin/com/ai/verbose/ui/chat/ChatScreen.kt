package com.ai.verbose.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ai.verbose.core.copyToClipboard
import com.ai.verbose.ui.theme.VerboseTheme
import com.ai.verbose.ui.theme.token.Black
import com.ai.verbose.ui.theme.token.Gray20
import com.ai.verbose.ui.theme.token.Gray80
import com.ai.verbose.ui.theme.token.Gray85
import com.ai.verbose.ui.theme.token.White
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import verbose.composeapp.generated.resources.Res
import verbose.composeapp.generated.resources.ic_clipboard
import verbose.composeapp.generated.resources.poppins_bold
import verbose.composeapp.generated.resources.poppins_medium

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    uiState: ChatUiState,
    onSubmitText: (query: String) -> Unit,
) {

    var inputText by remember { mutableStateOf(value = "") }

    val chatListState = rememberLazyListState()
    var selectedChatIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(uiState.chatList.size) {
        if (uiState.chatList.isNotEmpty()) {
            chatListState.animateScrollToItem(index = uiState.chatList.lastIndex)
            selectedChatIndex = -1
        }

        if (uiState.submitTextState == SubmitTextState.NORMAL)
            inputText = ""
    }

    Box(modifier = modifier.fillMaxSize().background(color = Black)) {

        if (uiState.chatList.isEmpty())
            EmptyState(modifier = Modifier.align(Alignment.Center))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .padding(end = 12.dp, start = 12.dp, top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.Bottom
                ),
                state = chatListState,
                horizontalAlignment = Alignment.End
            ) {

                itemsIndexed(items = uiState.chatList) { index, value ->
                    if (value.type == ChatTextType.CLIENT)
                        ClientChatText(
                            text = value.text,
                            isClipboardVisible = selectedChatIndex == index,
                            onClipBoardClick = {
                                selectedChatIndex =
                                    if (selectedChatIndex == index) -1 else index
                            })
                    else
                        AiChatText(
                            text = value.text,
                            isClipboardVisible = selectedChatIndex == index,
                            onClipBoardClick = {
                                selectedChatIndex =
                                    if (selectedChatIndex == index) -1 else index
                            }
                        )
                }

            }

            Footer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .offset(y = (-2).dp)
                    .background(shape = RoundedCornerShape(36.dp), color = Gray80),
                inputText = inputText,
                isEnabled = uiState.submitTextState == SubmitTextState.NORMAL,
                submitTextState = uiState.submitTextState,
                onSubmitClick = {
                    if (inputText.isNotEmpty() && uiState.submitTextState == SubmitTextState.NORMAL) {
                        onSubmitText(inputText)
                    }
                },
                onValueChange = { value ->
                    if (uiState.submitTextState == SubmitTextState.NORMAL) {
                        inputText = value
                    }
                }
            )

        }

    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    inputText: String,
    isEnabled: Boolean = true,
    submitTextState: SubmitTextState,
    onSubmitClick: () -> Unit,
    onValueChange: (value: String) -> Unit,
) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                modifier = Modifier.weight(.9F),
                value = inputText,
                enabled = isEnabled,
                onValueChange = {
                    onValueChange(it)
                },
                placeholder = {
                    Text(
                        text = "type...",
                        fontSize = 14.sp
                    )
                },
                textStyle = TextStyle.Default.copy(
                    fontFamily = FontFamily(Font(Res.font.poppins_medium)),
                    fontSize = 14.sp
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSearch = {}),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = White,
                    cursorColor = White,
                    focusedPlaceholderColor = Gray20,
                    disabledTextColor = White.copy(alpha = .5F),
                    disabledContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                maxLines = 1,
                minLines = 1
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .clickable {
                        onSubmitClick()
                    }
                    .background(
                        shape = CircleShape,
                        color = White.copy(alpha = if (submitTextState == SubmitTextState.NORMAL) 1F else .4F)
                    )
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.ArrowUpward,
                    tint = Gray80,
                    contentDescription = ""
                )
            }

        }

    }

}

@Composable
fun ClientChatText(
    modifier: Modifier = Modifier, text: String,
    isClipboardVisible: Boolean,
    onClipBoardClick: () -> Unit,
) {

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val density = LocalDensity.current
        val screenWidth = with(density) { maxWidth }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {

            AnimatedVisibility(
                visible = isClipboardVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                ClipboardBox(onClick = {
                    copyToClipboard(text = text)
                })
            }

            Text(
                modifier = Modifier
                    .widthIn(max = (screenWidth.value / 1.5).dp)
                    .clickable {
                        onClipBoardClick()
                    }
                    .background(
                        shape = RoundedCornerShape(28.dp),
                        color = Gray85
                    )
                    .padding(vertical = 12.dp, horizontal = 24.dp),
                text = text, color = White, fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.poppins_medium))
            )

        }
    }

}

@Composable
fun AiChatText(
    modifier: Modifier = Modifier,
    text: String,
    isClipboardVisible: Boolean,
    onClipBoardClick: () -> Unit,
) {

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val density = LocalDensity.current
        val screenWidth = with(density) { maxWidth }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {

            AiProfile()
            Spacer(modifier = Modifier.width(4.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    modifier = Modifier
                        .widthIn(max = (screenWidth.value / 1.5).dp)
                        .clickable {
                            onClipBoardClick()
                        }
                        .background(
                            shape = RoundedCornerShape(28.dp),
                            color = Gray85.copy(alpha = .5F)
                        )
                        .padding(vertical = 12.dp, horizontal = 24.dp),
                    text = text, color = White, fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_medium))
                )

                AnimatedVisibility(
                    visible = isClipboardVisible,
                    enter = slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    ClipboardBox(onClick = {
                        copyToClipboard(text = text)
                    })
                }
            }
        }
    }

}

@Composable
fun AiProfile(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Text(
            modifier = Modifier
                .background(
                    shape = CircleShape,
                    color = Gray85.copy(alpha = .5F)
                )
                .padding(vertical = 6.dp, horizontal = 12.dp),
            text = "AI",
            fontSize = 14.sp,
            color = White,
            fontFamily = FontFamily(Font(Res.font.poppins_medium))
        )

    }
}

@Composable
fun ClipboardBox(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Icon(
            modifier = Modifier.padding(12.dp),
            painter = painterResource(Res.drawable.ic_clipboard),
            tint = White,
            contentDescription = ""
        )

    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(16.dp),
                color = Gray85.copy(alpha = .4F)
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "No message here yet...",
            color = White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(Res.font.poppins_bold))
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Send a message and greeting with me",
            color = White,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.poppins_medium))
        )

    }

}

@Preview
@Composable
fun ScreenChantPreview() {
    VerboseTheme {
        ChatScreen(
            uiState = ChatUiState(),
            onSubmitText = { _ -> }
        )
    }
}