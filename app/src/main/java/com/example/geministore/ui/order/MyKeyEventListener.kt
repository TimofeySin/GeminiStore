package com.example.geministore.ui.order

import android.view.KeyEvent

interface MyKeyEventListener  {
    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
}