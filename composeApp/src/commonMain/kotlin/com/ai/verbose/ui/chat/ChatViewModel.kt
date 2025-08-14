package com.ai.verbose.ui.chat

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.ollama.client.OllamaClient
import ai.koog.prompt.llm.OllamaModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class ChatViewModel : ViewModel(), KoinComponent {

    private val prompt =
        "You are a helpful AI assistant. Keep your responses concise and friendly. Your name is Verbose and you are is come from Kotlin World. Be funny and cool."

    private val llmModel = OllamaModels.Alibaba.QWEN_2_5_05B

    private val ollamaClient = OllamaClient()

    private val _uiState = MutableStateFlow(value = ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun submitInputText(query: String) {
        viewModelScope.launch {
            val newList = _uiState.value.chatList.toMutableList()
            newList.add(ChatTextItem(text = query, type = ChatTextType.CLIENT))
            _uiState.update {
                it.copy(
                    chatList = newList,
                    submitTextState = SubmitTextState.PROCESSING
                )
            }
            getAiResponse(query = query)
        }
    }

    private suspend fun getAiResponse(query: String) = withContext(Dispatchers.IO) {
        try {
            val aiResponse = ollamaClient.execute(
                prompt = prompt("sample-chat") {
                    system(content = prompt)
                    user(content = query)
                },
                model = llmModel
            )

            val aiContent = extractContentFromResponse(aiResponse.toString())
            val newList = _uiState.value.chatList.toMutableList()
            newList.add(ChatTextItem(text = aiContent, type = ChatTextType.AI))
            _uiState.update {
                it.copy(
                    chatList = newList,
                    submitTextState = SubmitTextState.NORMAL
                )
            }
        } catch (e: Exception) {
            val aiContent =
                extractContentFromResponse(response = "Sorry, I encountered an error: ${e.message}")
            val newList = _uiState.value.chatList.toMutableList()
            newList.add(ChatTextItem(text = aiContent, type = ChatTextType.AI))
            _uiState.update {
                it.copy(
                    chatList = newList,
                    submitTextState = SubmitTextState.NORMAL
                )
            }
        }
    }

    /**
     * Extracts the content from the AI response string
     * Expected format: "[Assistant(content=Hello! I am Qwen..., metaInfo=..., attachments=[], finishReason=null)]"
     */
    private fun extractContentFromResponse(response: String): String {
        return try {
            val contentPattern = Regex("content=([^,]+)")
            val matchResult = contentPattern.find(response)

            if (matchResult != null) {
                matchResult.groupValues[1].trim()
            } else {
                val fallbackPattern = Regex("\\(([^)]+)\\)")
                val fallbackMatch = fallbackPattern.find(response)
                if (fallbackMatch != null) {
                    fallbackMatch.groupValues[1].trim()
                } else {
                    response
                }
            }
        } catch (_: Exception) {
            response
        }
    }

}