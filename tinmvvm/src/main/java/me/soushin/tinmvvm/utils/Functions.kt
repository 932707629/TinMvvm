package me.soushin.tinmvvm.utils

import android.view.View

fun throttleClick(wait: Long = 1000, block: ((View) -> Unit)): View.OnClickListener {
    return View.OnClickListener { v ->
        val current = System.currentTimeMillis()
        val lastClickTime = (v.getTag(v.id) as? Long) ?: 0
        if (current - lastClickTime > wait) {
            v.setTag(v.id, current)
            block(v)
        }
    }
}

fun debounceClick(wait: Long = 200, block: ((View) -> Unit)): View.OnClickListener {
    return View.OnClickListener { v ->
        var action = (v.getTag(v.id) as? DebounceAction)
        if(action == null){
            action = DebounceAction(v, block)
            v.setTag(v.id, action)
        }else{
            action.block = block
        }
        v.removeCallbacks(action)
        v.postDelayed(action, wait)
    }
}

class DebounceAction(val view: View, var block: ((View) -> Unit)): Runnable {
    override fun run() {
        if(view.isAttachedToWindow){
            block(view)
        }
    }
}

fun View.onClick(wait: Long = 200, block: ((View) -> Unit)) {
    setOnClickListener(throttleClick(wait, block))
}

fun View.onDebounceClick(wait: Long = 200, block: ((View) -> Unit)) {
    setOnClickListener(debounceClick(wait, block))
}









