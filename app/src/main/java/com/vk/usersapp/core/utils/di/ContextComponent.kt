package com.vk.usersapp.core.utils.di

import android.content.Context
import com.vk.usersapp.core.di.DIComponent
import com.vk.usersapp.core.di.LazyComponentHolder

interface ContextComponent : DIComponent {
    val context: Context
}

object ContextComponentHolder : LazyComponentHolder<ContextComponent>()