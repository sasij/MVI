package com.juanjo.demo.actions

import com.juanjo.demo.state.ModifyError
import com.juanjo.demo.state.PartialState
import com.juanjo.demo.utils.Logger
import io.reactivex.Observable

class HideError(private val logger: Logger = Logger()) : Action {
  override fun execute(): Observable<PartialState> {
    return Observable.just(ModifyError("") as PartialState)
      .doOnNext { logger.log("=> HideError") }
  }
}