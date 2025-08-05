package com.ai.verbose.core

import platform.UIKit.UIPasteboard

actual fun copyToClipboard(text: String) {
    UIPasteboard.generalPasteboard.string = text
}
