package com.juanjo.demo.actions

import com.juanjo.demo.state.ModifyBTCPrice
import com.juanjo.demo.state.ModifyLoading
import com.juanjo.demo.state.PartialState
import com.juanjo.demo.utils.Logger
import com.juanjo.demo.utils.Service
import com.juanjo.demo.utils.onBackground
import io.reactivex.Observable

class GetBTCPrice(private val service: Service, private val logger: Logger = Logger()) : Action {
  override fun execute(): Observable<PartialState> {
    return service.get()
      .onBackground()
      .toObservable()
      .doOnNext { logger.log("=> GePrice") }
      .map { price -> ModifyBTCPrice(price["USD"]?.toFloat() ?: 0.0F) as PartialState }
      .startWith(ModifyLoading(true) as PartialState)
      .doOnError { e -> println(e) }
  }
}