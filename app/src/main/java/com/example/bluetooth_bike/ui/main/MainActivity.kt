package com.example.bluetooth_bike.ui.main

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.bluetooth_bike.ui.bluetooth.BluetoothViewModel
import com.example.bluetooth_bike.ui.Navigation
import com.example.bluetooth_bike.ui.bluetooth.DevicesScreen
import com.example.bluetooth_bike.ui.theme.Bluetooth_bikeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object { const val TAG = "*** MainActivity" }
    //private val mainActivityViewModel: MainActivityViewModel by viewModels()
    val viewModel: BluetoothViewModel by viewModels()


    private val bluetoothManager by lazy {
        applicationContext.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val enableBluetoothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { /* Not needed */ }

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            val canEnableBluetooth =
                perms[Manifest.permission.BLUETOOTH_CONNECT] == true

            if(canEnableBluetooth && !isBluetoothEnabled) {
                enableBluetoothLauncher.launch(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                )
            }
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
            )
        )

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
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bluetooth_bikeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }

        //initBluetoothAdapter()
    }*/
/*
    private var requestBluetooth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.v(TAG, "Bluetooth enabled")
            } else {
                Log.v(TAG, "Bluetooth not enabled")
            }
        }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.v(TAG, "permission granted")
        } else {
            Log.v(TAG, "permission not granted")
        }
    }



private fun initBluetoothAdapter() {
    val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    if (bluetoothAdapter == null) {
        Log.v(TAG, "Bluetooth not supported")
    } else {
        enableBluetooth(bluetoothAdapter)
    }
}

private fun enableBluetooth(bluetoothAdapter: BluetoothAdapter) {
    if (!bluetoothAdapter.isEnabled) {

        Log.v(TAG, "bluetooth disabled")
        requestBluetoothPermission()
        requestBluetooth.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))

    } else {
        Log.v(TAG, "bluetooth is enabled")
        getPairedDevices(bluetoothAdapter)
    }
}

private fun requestBluetoothPermission() {
    requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
}

private fun getPairedDevices(bluetoothAdapter: BluetoothAdapter) : Set<BluetoothDevice> {

    val pairedDevices: Set<BluetoothDevice> =
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestBluetoothPermission()
            Log.v(TAG, "paired devices: -")

            return emptySet()
        } else
            bluetoothAdapter.bondedDevices

    Log.v(TAG, "paired devices: ${pairedDevices.size}")
    return pairedDevices
}*/
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}