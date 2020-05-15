package com.juanjo.demo

import android.app.Application
import com.juanjo.demo.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DemoApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      printLogger()
      androidContext(this@DemoApplication)
      modules(
        listOf(
          applicationModule
        )
      )
    }
  }
}