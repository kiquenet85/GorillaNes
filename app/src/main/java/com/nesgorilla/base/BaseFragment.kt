package com.nesgorilla.base

import androidx.fragment.app.Fragment
import com.nesgorilla.di.AppComponent
import com.nesgorilla.manager.ResourceManager

open class BaseFragment : Fragment(){
    protected val resourceManager: ResourceManager = AppComponent.getInstance().provideResourceManager()
}