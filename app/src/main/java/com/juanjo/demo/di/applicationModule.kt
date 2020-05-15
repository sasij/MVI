package com.juanjo.demo.di

import com.juanjo.demo.state.State
import com.juanjo.demo.store.Store
import com.juanjo.demo.utils.Logger
import org.koin.dsl.module

val applicationModule = module {
  single { Store() }
  single { Logger() }
  single { State() }
}