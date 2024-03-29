package com.example.bluetooth_bike.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): com.example.bluetooth_bike.domain.bluetooth.BluetoothController {
        return com.example.bluetooth_bike.data.bluetooth.BluetoothController(context)
    }
}