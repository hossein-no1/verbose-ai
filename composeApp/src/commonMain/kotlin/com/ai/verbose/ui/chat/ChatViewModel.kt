package com.ai.verbose.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val mockAiResponse = listOf(
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum cursus, nibh a feugiat pharetra, velit purus sodales urna, ut scelerisque turpis ligula nec nisi. Nullam sed neque id nulla fermentum fermentum vel vitae felis. Aenean tincidunt bibendum tellus in finibus. Nam auctor lectus sed diam sodales feugiat. Integer porttitor turpis a justo luctus, quis ornare purus consequat. Maecenas ullamcorper felis nec interdum viverra. Donec lacinia justo et urna convallis, ac venenatis mi ultricies. Sed tristique tellus in urna blandit, et vehicula sapien aliquam. Aliquam luctus justo eu magna aliquet ultricies. Duis cursus turpis nec fermentum vulputate.",
    "Suspendisse potenti. Sed a urna at dui feugiat condimentum id sed purus. Donec vehicula, magna sed aliquet suscipit, erat orci vulputate justo, a rhoncus erat quam eget velit. Proin fermentum lectus in odio sollicitudin, nec hendrerit erat congue. Integer egestas quam sit amet ante volutpat vulputate. Nullam vehicula nisi et orci vehicula, id fermentum lacus iaculis. Sed nec orci nec metus efficitur consequat nec id nulla. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce ut ante non nunc lacinia scelerisque. Nulla tincidunt libero non nisi venenatis, vel interdum enim scelerisque.",
    "Quisque suscipit purus ut velit fringilla, eget viverra justo elementum. Praesent faucibus nulla vitae lectus volutpat, non feugiat sapien vehicula. Aenean lobortis ligula id risus bibendum, id vehicula nisi iaculis. Phasellus sed tortor vitae augue luctus maximus. Etiam scelerisque urna in tortor pharetra, a sodales libero elementum. Fusce ac libero sit amet neque dapibus egestas nec ac tortor. Sed at justo sit amet lectus sodales finibus. Nam fringilla dui sed odio gravida, a volutpat odio malesuada. Nullam dictum nisi eget arcu ultricies, id finibus dolor iaculis. Etiam sollicitudin dui sed eros suscipit, at feugiat enim aliquet."
)

class ChatViewModel : ViewModel() {

    var inputText by mutableStateOf("")
    var uiState by mutableStateOf(ChatUiState())
        private set

    fun submitInputText(onComplete: () -> Unit) {
        viewModelScope.launch {

            val newList = uiState.chatList.toMutableList()
            newList.add(ChatTextItem(text = inputText, type = ChatTextType.CLIENT))
            uiState = uiState.copy(
                chatList = newList,
                submitTextState = SubmitTextState.PROCESSING
            )
            inputText = ""
            getAiResponse()
            onComplete()

        }
    }

    private suspend fun getAiResponse() {

        val newList = uiState.chatList.toMutableList()
        delay((1000..5000).random().toLong())

        newList.add(ChatTextItem(text = mockAiResponse.random(), type = ChatTextType.AI))
        uiState = uiState.copy(
            chatList = newList,
            submitTextState = SubmitTextState.NORMAL
        )

    }

}