package com.example.bluetooth_bike.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel(){
    companion object { const val TAG = "*** MainActivityViewModel" }
}