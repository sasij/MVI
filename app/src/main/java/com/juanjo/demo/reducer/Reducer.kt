package com.juanjo.demo.reducer

import com.juanjo.demo.state.*

object Reducer {
  fun reduce(state: State, partialState: PartialState): State =
    when (partialState) {
      is ModifyBTCPrice -> {
        state.copy(btcCurrentPrice = partialState.price, loading = false)
      }
      is ModifyLoading -> {
        state.copy(loading = partialState.value)
      }
      is ModifyBalance -> {
        state.copy(result = (state.btcCurrentPrice * partialState.balance))
      }
      is ModifyError -> {
        state.copy(error = partialState.error, loading = false)
      }
    }
}