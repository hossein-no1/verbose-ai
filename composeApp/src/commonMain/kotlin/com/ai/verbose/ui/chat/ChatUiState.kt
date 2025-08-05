package com.ai.verbose.ui.chat

data class ChatUiState(
    var chatList: List<ChatTextItem> = emptyList(),
    val submitTextState: SubmitTextState = SubmitTextState.NORMAL,
)

enum class SubmitTextState {
    NORMAL,
    PROCESSING
}