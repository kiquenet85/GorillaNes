package com.nesgorilla.base

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.nesgorilla.di.AppComponent
import com.nesgorilla.manager.ResourceManager

open class BaseFragment : Fragment(){
    protected val resourceManager: ResourceManager = AppComponent.getInstance().provideResourceManager()

    open fun clearKeyboardFromScreen(activity: Activity) {
        if (activity.window.currentFocus != null) {
            val imm = activity.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.currentFocus!!.windowToken, 0)
        }
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}