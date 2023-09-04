package com.example.bluetooth_bike.data.network

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

//sealed class for the different types of errors
sealed class AuthError {
    object NoError : AuthError()
    object MailNoVerification : AuthError()
    object ErrorMailOrPass : AuthError()
    object ErrorVERIFICATION : AuthError() // Unable to send verification email
}

@Singleton
class AuthenticationService @Inject constructor(
    private val firebase: FirebaseClient
) {

    companion object {
        const val TAG = "AuthenticationService **"
        const val NO_ERROR = 0
        const val MAIL_NO_VERIFICATION = 100
        const val ERROR_MAIL_OR_PASS = 101
        const val ERROR_CREATING_ACCOUNT = 102 //Error occurred creating account
    }

    fun createUser(email: String, pass: String, userName: String): Int {
        val auth = firebase.auth
        var error = NO_ERROR

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                putUserName(userName)
                sendMailVerification()
                Log.v(TAG, "OK: createUserWithEmail:success ${auth.currentUser?.toString()}")
            } else if (it.isCanceled) {
                Log.e(TAG, "Error: createUserWithEmail:canceled -->${it.exception}")
                error = ERROR_CREATING_ACCOUNT
            } else {
                Log.e(TAG, "Error: createUserWithEmail:failure")
                error = ERROR_CREATING_ACCOUNT
            }
        }
        return error
    }

    private fun putUserName(name: String) {
        val profileUpdates = userProfileChangeRequest { displayName = name }

        Firebase.auth.currentUser!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful)
                Log.v(TAG, "OK: User profile updated correctly.")
            else
                Log.e(TAG, "Error: User profile update error")
        }
    }

    private fun sendMailVerification() {

        Firebase.auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
             if (it.isSuccessful) {
                Log.v(TAG, "OK: sendEmailVerification:Success") //todo back sealed class AuthError
            } else {
                Log.e(TAG, "Error: sendEmailVerification:fail")
            }
        }
    }

    fun mailPassLogin(loginMail: String, loginPass: String, completionHandler: (Int) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(loginMail, loginPass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (!Firebase.auth.currentUser?.isEmailVerified!!) {
                        completionHandler(MAIL_NO_VERIFICATION)
                        Log.v(TAG, "Login success but mail no verification")
                    } else {
                        completionHandler(NO_ERROR)
                        Log.v(TAG, "Login success")
                    }
                } else if (task.isCanceled || task.isComplete) {
                    completionHandler(ERROR_MAIL_OR_PASS)
                    Log.v(TAG, "Login failed")
                }
            }
    }
}