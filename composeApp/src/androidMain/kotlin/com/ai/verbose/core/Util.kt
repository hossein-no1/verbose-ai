package com.ai.verbose.core

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private class ClipboardHelper : KoinComponent {
    private val context: Context by inject()

    fun copy(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }
}

actual fun copyToClipboard(text: String) {
    ClipboardHelper().copy(text)
}