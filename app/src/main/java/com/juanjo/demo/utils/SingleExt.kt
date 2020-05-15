package com.juanjo.demo.utils

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.onBackground(): Single<T> =
  subscribeOn(Schedulers.computation())
    .observeOn(AndroidSchedulers.mainThread())