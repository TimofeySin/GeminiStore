package com.example.geministore.ui


import android.view.KeyEvent
import androidx.fragment.app.Fragment

abstract  class BaseFragment():Fragment() {

    open fun keyDown(keyCode: Int, event: KeyEvent?): Boolean{
        return true
    }
}