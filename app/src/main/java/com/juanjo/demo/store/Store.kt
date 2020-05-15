package com.juanjo.demo.store

import com.juanjo.demo.actions.Action
import com.juanjo.demo.reducer.Reducer
import com.juanjo.demo.state.State
import com.juanjo.demo.utils.Logger
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class Store(
  private val state: State = State(),
  private val logger: Logger = Logger(),
  private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
  private val ioScheduler: Scheduler = Schedulers.computation()
) {
  private var inPipeline: PublishSubject<Action> = PublishSubject.create()
  private var outPipeline: BehaviorSubject<State> = BehaviorSubject.create()
  private var disposable: Disposable? = null

  init {
    mainPipe()
  }

  fun getDispatch(action: Action) {
    inPipeline.onNext(action)
  }

  //the name should be "subscribe" but...it is horrible when we use on the view
  fun changes() = outPipeline.hide()

  private fun mainPipe() {
    disposable = inPipeline
      .switchMap { it.execute() }
      .scan(state) { state, action -> Reducer.reduce(state, action) }
      .distinctUntilChanged()
      .doOnNext {
        //TODO save states in a list for debugging
        //state = it
        //TODO push to fabric
        logger.log(it.toString())
      }
      .subscribeOn(ioScheduler)
      .observeOn(uiScheduler)
      .subscribe { outPipeline.onNext(it) }
  }
}