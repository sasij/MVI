package com.juanjo.demo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juanjo.demo.actions.CalculateBalance
import com.juanjo.demo.actions.GetBTCPrice
import com.juanjo.demo.actions.HideError
import com.juanjo.demo.actions.ShowError
import com.juanjo.demo.databinding.MainViewBinding
import com.juanjo.demo.state.State
import com.juanjo.demo.store.Store
import com.juanjo.demo.utils.Service
import com.juanjo.demo.utils.visible
import com.juanjo.demo.utils.with
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject

class MainView : AppCompatActivity() {
  private val store: Store by inject()
  private var binding: MainViewBinding? = null
  private var disposable: Disposable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = MainViewBinding.inflate(layoutInflater)
    setContentView(binding?.root)
    subscribeToStateChanges()
    setListener()
  }

  private fun subscribeToStateChanges() {
    disposable = store.changes().subscribe { render(it) }
  }

  //Welcome to Compose
  private fun render(state: State) {
    binding?.btcCurrentPriceTextview?.with(state.btcCurrentPrice.toString())
    binding?.result?.with(state.result.toString())
    binding?.loading?.visible(state.loading)
    binding?.errorWrapper?.with(binding?.error, state.error)
  }

  private fun setListener() {
    binding?.button1?.setOnClickListener {
      store.getDispatch(
        GetBTCPrice(Service())
      )
    }
    binding?.button2?.setOnClickListener {
      store.getDispatch(
        CalculateBalance((binding?.value?.text.toString().toFloat()))
      )
    }
    binding?.button3?.setOnClickListener {
      store.getDispatch(
        ShowError("Any error happened!!")
      )
    }

    binding?.dismissError?.setOnClickListener {
      store.getDispatch(HideError())
    }
  }

  override fun onStop() {
    disposable?.dispose()
    super.onStop()
  }

  override fun onDestroy() {
    binding = null
    super.onDestroy()
  }
}


