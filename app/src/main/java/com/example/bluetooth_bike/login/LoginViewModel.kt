package com.example.bluetooth_bike.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel {

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isLoginEnabled = MutableLiveData<Boolean>(false)
    val isLoginEnabled : LiveData <Boolean> = _isLoginEnabled

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password

        _isLoginEnabled.value = enableLogin(email, password)
    }


    fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

}