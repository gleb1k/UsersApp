package com.vk.usersapp

import android.content.Context
import com.vk.usersapp.core.network.di.NetworkComponent
import com.vk.usersapp.core.network.di.NetworkComponentHolder
import com.vk.usersapp.core.network.NetworkFactory
import com.vk.usersapp.core.utils.di.ContextComponent
import com.vk.usersapp.core.utils.di.ContextComponentHolder
import retrofit2.Retrofit

class AppInitializer {

    fun initializeDIComponents(appContext: Context) {
        ContextComponentHolder.set {
            object : ContextComponent {
                override val context: Context = appContext
            }
        }
        NetworkComponentHolder.set {
            val retrofit = NetworkFactory().create()
            object : NetworkComponent {
                override val retrofit: Retrofit = retrofit
            }
        }
    }
}