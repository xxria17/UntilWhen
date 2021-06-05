package com.dhxxn.untilwhenaos

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.Window

class KeyboardVisibilityUtils(
        private val window: Window,
        private val onShowKeyboard: ((keyboardHeight: Int) -> Unit?)? = null,
        private val onHideKeyboard:(()-> Unit)? = null
) {
    private val MIN_KEYBOARD_HEIGHT_PX = 150
    private val windowVisibleDisplayFrame = Rect()
    private var lastVisibleViewHeight: Int = 0

    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        window.decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame)
        val visibleDecorViewHeight = windowVisibleDisplayFrame.height()

        if (lastVisibleViewHeight != 0) {
            if (lastVisibleViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                val currentKeyboardHeight = window.decorView.height - windowVisibleDisplayFrame.bottom
                onShowKeyboard?.invoke(currentKeyboardHeight)

            } else if (lastVisibleViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                onHideKeyboard?.invoke()
            }
        }
        lastVisibleViewHeight = visibleDecorViewHeight
    }

    init {
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    fun detachKeyboardListeners() {
        window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}