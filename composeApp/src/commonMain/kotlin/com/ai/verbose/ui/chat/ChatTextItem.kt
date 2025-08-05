package com.ai.verbose.ui.chat

data class ChatTextItem(
    val text : String,
    val type: ChatTextType,
    val isClipboardVisible : Boolean = false
)

enum class ChatTextType{
    CLIENT,
    AI
}