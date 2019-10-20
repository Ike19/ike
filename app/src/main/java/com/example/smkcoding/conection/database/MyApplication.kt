package com.example.smkcoding.conection.database

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        Stetho.initializeWithDefaults(this)
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                    Stetho.defaultDumperPluginsProvider(this)
                )
                .enableWebKitInspector(
                    Stetho.defaultInspectorModulesProvider(this)
                )
                .build()
        )
    }

    companion object {
        var context: Context? = null
            private set
    }

    override fun attachBaseContext(base: Context?) {

    }
}

