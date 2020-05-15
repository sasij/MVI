package com.juanjo.demo.actions

import com.juanjo.demo.state.ModifyLoading
import com.juanjo.demo.state.PartialState
import com.juanjo.demo.utils.Logger
import io.reactivex.Observable

class Loading(private val value: Boolean, private val logger: Logger = Logger()) : Action {
  override fun execute(): Observable<PartialState> {
    return Observable.just(value)
      .map { isLoading -> ModifyLoading(isLoading) as PartialState }
      .doOnNext { logger.log("=> Loading") }
  }
}