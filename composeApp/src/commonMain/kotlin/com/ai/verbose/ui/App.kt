package com.ai.verbose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ai.verbose.ui.chat.ChatScreen
import com.ai.verbose.ui.chat.ChatViewModel
import com.ai.verbose.ui.theme.VerboseTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    VerboseTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val viewModel : ChatViewModel = koinViewModel<ChatViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            ChatScreen(
                modifier = Modifier.padding(innerPadding),
                uiState = uiState,
                onSubmitText = viewModel::submitInputText
            )
        }
    }
}