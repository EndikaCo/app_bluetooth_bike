package com.example.bluetooth_bike.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetooth_bike.domain.bluetooth.BluetoothController
import com.example.bluetooth_bike.domain.bluetooth.ConnectionResult
import com.example.bluetooth_bike.domain.model.BluetoothUiState
import com.example.bluetooth_bike.domain.model.BtDevice
import com.example.bluetooth_bike.domain.model.TimeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val bluetoothController: BluetoothController
): ViewModel() {

    private val _state = MutableStateFlow(BluetoothUiState())
    val state = combine(
        bluetoothController.scannedDevices,
        bluetoothController.pairedDevices,
        bluetoothController.isScanning,
        _state
    ) { scannedDevices, pairedDevices, isScanning,  state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices,
            isScanning = isScanning,
            values = if (state.isConnected) state.values else emptyList(),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    private var deviceConnectionJob: Job? = null

    init {
        bluetoothController.isConnected.onEach { isConnected ->
            _state.update { it.copy(isConnected = isConnected) }
        }.launchIn(viewModelScope)

        bluetoothController.errors.onEach { error ->
            _state.update { it.copy(
                errorMessage = error
            ) }
        }.launchIn(viewModelScope)

        //call every 60 seconds
        viewModelScope.launch {
            while(true) {
                updateTime()
                kotlinx.coroutines.delay(60000)
            }
        }
    }

    private fun updateTime()  {
        val currentDateTime = getCurrentDateTime()
        val dayOfWeek = formatDayOfWeek(currentDateTime)
        val dayOfMonth = currentDateTime.dayOfMonth.toString()
        val month = formatMonth(currentDateTime)
        val hour = formatHour(currentDateTime)

        _state.update { it.copy(
            time = TimeModel(
                day = dayOfWeek,
                hour = hour,
                date = "$dayOfMonth $month"
            )
        ) }
        }


    fun getCurrentDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    fun formatDayOfWeek(datetime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("EEE")
        return datetime.format(formatter).uppercase(Locale.getDefault())
    }

    fun formatHour(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return dateTime.format(formatter)
    }

    fun formatMonth(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MMM")
        return dateTime.format(formatter).uppercase(Locale.getDefault())
    }

    fun connectToDevice(device: BtDevice) {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothController
            .connectToDevice(device)
            .listen()
    }

    fun disconnectFromDevice() {
        deviceConnectionJob?.cancel()
        bluetoothController.closeConnection()
        _state.update { it.copy(
            isConnecting = false,
            isConnected = false
        ) }
    }

    fun waitForIncomingConnections() {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothController
            .startBluetoothServer()
            .listen()
    }

    fun cancelServer(){
        deviceConnectionJob?.cancel()
        _state.update { it.copy(
            isConnecting = false,
        ) }
    }

    fun scanToggle(){
        if (bluetoothController.isScanning.value)
            stopScan()
        else
            startScan()
    }

    fun sendData(message: String) { //todo
        viewModelScope.launch {
            val bluetoothMessage = bluetoothController.trySendMessage(message)
            if(bluetoothMessage != null) {
                _state.update { it.copy(
                    values = it.values + bluetoothMessage
                ) }
            }
        }
    }

    private fun startScan() {
        bluetoothController.startDiscovery()
    }

    private fun stopScan() {
        bluetoothController.stopDiscovery()
    }

    private fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when(result) {
                ConnectionResult.ConnectionEstablished -> {
                    _state.update { it.copy(
                        isConnected = true,
                        isConnecting = false,
                        errorMessage = null
                    ) }
                }
                is ConnectionResult.TransferSucceeded -> {
                    _state.update { it.copy(
                        values = it.values + result.message
                    ) }
                }
                is ConnectionResult.Error -> {
                    _state.update { it.copy(
                        isConnected = false,
                        isConnecting = false,
                        errorMessage = result.message
                    ) }
                }
            }
        }
            .catch {
                bluetoothController.closeConnection()
                _state.update { it.copy(
                    isConnected = false,
                    isConnecting = false,
                ) }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        bluetoothController.release()
    }
}