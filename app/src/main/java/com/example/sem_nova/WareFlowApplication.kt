package com.example.sem_nova

import android.app.Application
import com.example.sem_nova.Data.AppContainer
import com.example.sem_nova.Data.AppDataContainer

class WareFlowApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}