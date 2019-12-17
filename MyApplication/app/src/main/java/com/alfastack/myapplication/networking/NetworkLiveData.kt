package com.alfastack.myapplication.networking

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

/**
 * Created by Joro on 17/12/2019
 */
class NetworkLiveData constructor(application: Application) : LiveData<CONNECTION>() {

    private val networkRequest: NetworkRequest.Builder = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

    private val cManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun onActive() {
        super.onActive()
        init()
    }

    private fun init() {
        cManager.registerNetworkCallback(
            networkRequest.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(CONNECTION.AVAILABLE)
                }

                override fun onLost(network: Network) {
                    if (cManager.allNetworks.isEmpty()) {
                        super.onLost(network)
                        postValue(CONNECTION.LOST)
                    }
                }

                override fun onUnavailable() {
                    if (cManager.allNetworks.isEmpty()) {
                        super.onUnavailable()
                        postValue(CONNECTION.UNAVAILABLE)
                    }
                }
            })
    }
}

enum class CONNECTION {
    AVAILABLE,
    LOST,
    UNAVAILABLE
}