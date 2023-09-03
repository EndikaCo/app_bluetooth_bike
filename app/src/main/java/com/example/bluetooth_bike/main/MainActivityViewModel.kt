package com.example.bluetooth_bike.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MainActivityViewModel : ViewModel() {
    companion object { const val TAG = "*** MainActivityViewModel" }

}