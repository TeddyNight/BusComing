package com.github.teddynight.buscoming.utlis

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import java.util.function.BiFunction

/*
Reference: https://stackoverflow.com/questions/60629906/how-to-broadcast-receiver-and-mvvm
 */

class ReceiverLiveData<T>(val context: Context,
                      val filter: IntentFilter,
                      val mapFunc: BiFunction<Context, Intent, T>)
    : LiveData<T>(){

    private val mBroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            value = mapFunc.apply(context, intent)
        }
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(mBroadcastReceiver)
    }

    override fun onActive() {
        super.onActive()
        value = mapFunc.apply(context, Intent())
        context.registerReceiver(mBroadcastReceiver,filter)
    }

}