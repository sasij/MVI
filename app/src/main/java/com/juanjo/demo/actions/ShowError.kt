package com.juanjo.demo.actions

import com.juanjo.demo.state.ModifyError
import com.juanjo.demo.state.PartialState
import com.juanjo.demo.utils.Logger
import io.reactivex.Observable

class ShowError(private val errorMessage: String, private val logger: Logger = Logger()) : Action {
  override fun execute(): Observable<PartialState> {
    return Observable.just((ModifyError(errorMessage)) as PartialState)
      .doOnNext { logger.log("=> ShowError") }
  }
}
