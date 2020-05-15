# MVI
Simple Model View Intent example using Kotlin



### Example of Action

```kotlin
 override fun execute(): Observable<PartialState> {
   return service.get()
        .onBackground()
        .toObservable()
        .map { price -> ModifyBTCPrice(price[“USD”]?.toFloat()) as PartialState }
        .startWith(ModifyLoading(true) as PartialState)
        .doOnError { e -> println(“Ouuu yeah!!…”)) }
        .doOnNext { logger.log("=> GePrice") }
    }
 }
```

### Store to save your state, listen for action and publish changes

```kotlin
  
  fun getDispatch(action: Action) {
    inPipeline.onNext(action)
  }
  
  fun changes() = outPipeline.hide()
  
  private fun mainPipe() {
    disposable = inPipeline
      .switchMap { it.execute() }
      .scan(state) { state, action -> Reducer.reduce(state, action) }
      .distinctUntilChanged()
      .doOnNext {
        logger.log(it.toString())
      }
      .subscribeOn(ioScheduler)
      .observeOn(uiScheduler)
      .subscribe { outPipeline.onNext(it) }
  }
```

### Pure reducer to generate new states 

```kotlin
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
```

### Why you should use MVI?
* Unidirectional flow
* One source of truth. No more flags, arrays, variables...everywhere 
* No more callbacks to check if the view is not null or attached
* Debug your code like a Pro
* Easy to tests. 
* If you love Rx. To use corutine here...weeeell you can...
 
### Why you should not use
* Model your app like a state machine, it is harder
* Boilerplate, a simple boolean change needs an action, add a new entry in your reducer and take care about it on yout render method
* Heavy memory use. We do not update the state, we create a new one every time we need a modification
* Heavy render method...(Jetpack compose maybe helps)

Want to contribute? Great!
