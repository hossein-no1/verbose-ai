package com.ai.verbose.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.ai.verbose.ui.theme.token.Pink40
import com.ai.verbose.ui.theme.token.Pink80
import com.ai.verbose.ui.theme.token.Purple40
import com.ai.verbose.ui.theme.token.Purple80
import com.ai.verbose.ui.theme.token.PurpleGrey40
import com.ai.verbose.ui.theme.token.PurpleGrey80

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val LocalSystemBarsPadding = compositionLocalOf { PaddingValues() }

@Composable
fun VerboseTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}