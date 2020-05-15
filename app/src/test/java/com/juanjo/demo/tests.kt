package com.juanjo.demo

import com.juanjo.demo.actions.*
import com.juanjo.demo.state.State
import com.juanjo.demo.store.Store
import com.juanjo.demo.utils.Logger
import com.juanjo.demo.utils.Service
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

  @get:Rule
  var testSchedulerRule = RxImmediateSchedulerRule()

  private lateinit var store: Store
  private lateinit var testObserver: TestObserver<State>
  val logger = mock<Logger>()
  val service = mock<Service>()
  val ANY_ERROR_MESSAGE = "ANY ERROR"

  @Before
  fun setup() {
    store = Store(logger = logger)
    testObserver = store.changes().test()
  }

  @Test
  fun `should show loading view`() {
    store.getDispatch(Loading(true, logger))
    testObserver.assertValueAt(1) { it.loading }
  }

  @Test
  fun `should hide loading `() {
    store.getDispatch(Loading(true, logger))
    store.getDispatch(Loading(false, logger))
    testObserver.assertValueAt(2) { it.loading.not() }
  }

  @Test
  fun `should show any error message `() {
    store.getDispatch(ShowError(ANY_ERROR_MESSAGE, logger))
    testObserver.assertValueAt(1) { it.error == ANY_ERROR_MESSAGE }
  }

  @Test
  fun `should hide the error message `() {
    store.getDispatch(ShowError(ANY_ERROR_MESSAGE, logger))
    store.getDispatch(HideError(logger))
    testObserver.assertValueAt(2) { it.error == "" }
  }

  @Test
  fun `should show a BTC price  `() {
    whenever(service.get()).thenReturn(Single.just(mapOf(Pair("USD", "5000"))))
    store.getDispatch(GetBTCPrice(service, logger))
    testObserver.assertValueAt(2) { it.btcCurrentPrice == 5000F }
  }

  @Test
  fun `should modify the balance `() {
    whenever(service.get()).thenReturn(Single.just(mapOf(Pair("USD", "5000"))))
    store.getDispatch(GetBTCPrice(service, logger))
    store.getDispatch(CalculateBalance(0.5F, logger))
    testObserver.assertValueAt(3) { it.result == 2500F }
  }
}
