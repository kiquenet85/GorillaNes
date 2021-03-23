package com.nesgorilla

import android.app.Application
import com.nesgorilla.di.AppComponent

class GorillaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDIModules()
    }

    private fun initDIModules() {
        AppComponent.initialize(this)
    }
}