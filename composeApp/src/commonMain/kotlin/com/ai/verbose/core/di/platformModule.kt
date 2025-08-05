package com.ai.verbose.core.di

import com.ai.verbose.ui.chat.ChatViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module { viewModelOf(::ChatViewModel) }