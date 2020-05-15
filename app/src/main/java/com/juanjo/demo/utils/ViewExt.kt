package com.juanjo.demo.utils

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

fun View.visible(value: Boolean) {
  if (value) {
    this.visibility = View.VISIBLE
  } else {
    this.visibility = View.GONE
  }
}

fun TextView.with(text: String) {
  if (text.isEmpty()) {
    this.visible(false)
  } else {
    this.text = text
    this.visible(true)
  }
}

fun ConstraintLayout.with(view: TextView?, text: String) {
  if (text.isEmpty()) {
    this.visible(false)
  } else {
    view?.with(text)
    this.visible(true)
  }
}
