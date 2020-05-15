package com.juanjo.demo.state

data class State(
  val btcCurrentPrice: Float = 0.0F,
  val result: Float = 0.0F,
  val loading: Boolean = false,
  val error: String = ""
)

sealed class PartialState
data class ModifyBTCPrice(val price: Float) : PartialState()
data class ModifyLoading(val value: Boolean) : PartialState()
data class ModifyBalance(val balance: Float) : PartialState()
data class ModifyError(val error: String) : PartialState()