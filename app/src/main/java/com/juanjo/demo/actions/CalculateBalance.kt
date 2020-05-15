package com.juanjo.demo.actions

import com.juanjo.demo.state.ModifyBalance
import com.juanjo.demo.state.PartialState
import com.juanjo.demo.utils.Logger
import io.reactivex.Observable

class CalculateBalance(private val balance: Float, private val logger: Logger = Logger()) : Action {
  override fun execute(): Observable<PartialState> {
    return Observable.just(balance)
      .map { balance -> ModifyBalance(balance) as PartialState }
      .doOnNext { logger.log("=> CalculateBalance") }
  }
}
