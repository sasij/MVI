package com.juanjo.demo.actions

import com.juanjo.demo.state.PartialState
import io.reactivex.Observable

interface Action {
  fun execute(): Observable<PartialState>
}







