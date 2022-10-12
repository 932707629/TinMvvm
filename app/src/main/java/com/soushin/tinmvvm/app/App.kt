package com.soushin.tinmvvm.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import me.soushin.tinmvvm.base.BaseApp
import me.soushin.tinmvvm.config.AppDelegate

class App : BaseApp() {

    override fun attachBaseContext(base: Context) {
        MultiDex.install(base)
        super.attachBaseContext(base)
    }


}