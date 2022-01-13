package com.jayesh.shadi.callback
import com.jayesh.shadi.model.Results


interface ModelCallbacks {
    fun onSuccess(response: Results?)
    fun onError(error: String?)
}





