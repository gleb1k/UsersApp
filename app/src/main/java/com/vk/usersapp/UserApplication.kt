package com.vk.usersapp

import android.app.Application

class UserApplication : Application() {

    private val initializer by lazy { AppInitializer() }

    override fun onCreate() {
        super.onCreate()
        initializer.initializeDIComponents(this)
    }
}