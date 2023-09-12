package com.example.bluetooth_bike.ui.main

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bluetooth_bike.ui.bluetooth.BluetoothViewModel
import com.example.bluetooth_bike.ui.Navigation
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "*** MainActivity"
    }

    private val viewModel: BluetoothViewModel by viewModels()

    private val bluetoothManager by lazy { applicationContext.getSystemService(BluetoothManager::class.java) }
    private val bluetoothAdapter by lazy { bluetoothManager?.adapter }
    private val isBluetoothEnabled: Boolean get() = bluetoothAdapter?.isEnabled == true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBluetooth()

        setContent {
            Bluetooth_bikeTheme {

                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(viewModel)
                }
            }
        }
    }

    private fun initBluetooth() {

        val enableBluetoothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.v(TAG, "$result")
        }

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val canEnableBluetooth = permissions[Manifest.permission.BLUETOOTH_CONNECT] == true

            if (canEnableBluetooth && !isBluetoothEnabled) {
                enableBluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}