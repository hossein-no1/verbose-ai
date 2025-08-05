package com.ai.verbose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ai.verbose.ui.chat.ChatScreen
import com.ai.verbose.ui.theme.VerboseTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    VerboseTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ChatScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}