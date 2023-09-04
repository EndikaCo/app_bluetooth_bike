package com.example.bluetooth_bike.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String> = _repeatPassword

    private val _isSignInEnabled = MutableLiveData<Boolean>(false)
    val isSignUpEnabled: LiveData<Boolean> = _isSignInEnabled

    fun onSignUpChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isSignInEnabled.value = enableSignUp(email, password)
    }

    fun enableSignUp(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

}
