package com.nesgorilla.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nesgorilla.base.error.ErrorHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

abstract class BaseCoroutineViewModel(errorHandler: ErrorHandler) : ViewModel() {
    open val errorHandler = CoroutineExceptionHandler { coroutineScope, exception ->
        viewModelScope.launch {
            try {
                errorState.value =
                    CommonErrorState.CriticalHandledUIError("Something went wrong please try later!")
                errorHandler.reportCriticalException(
                    exception,
                    "Handled exception on coroutine builder"
                )
            } catch (reportException: Exception) {
                val message = "Error handling exception on coroutine error handler"
                errorState.value = CommonErrorState.CriticalUIError(message)
                errorHandler.reportErrorHandlingException(exception, message)
            }
        }
    }

    private val errorState: MutableLiveData<CommonErrorState> = MutableLiveData()
    fun getErrorState(): LiveData<CommonErrorState> = errorState
}

sealed class CommonErrorState(open val message: String) {
    class CriticalHandledUIError(override val message: String) : CommonErrorState(message)
    class CriticalUIError(override val message: String) : CommonErrorState(message)
}