package com.pict.pbl.medswift.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.MutableLiveData

class Utils {

    companion object {

        val isConnected = MutableLiveData( false )

        fun isConnected( context: Context) : Boolean {
            // TODO: Read this -> https://stackoverflow.com/questions/32547006/connectivitymanager-getnetworkinfoint-deprecated
            return isConnectedWithNetwork( context , null )
        }

        private fun isConnectedWithNetwork( context: Context , network : Network? ) : Boolean {
            val connectivityManager = context.getSystemService( Context.CONNECTIVITY_SERVICE ) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if ( network == null ) {
                    connectivityManager.getNetworkCapabilities( connectivityManager.activeNetwork )?.run {
                        return hasTransport( NetworkCapabilities.TRANSPORT_WIFI )
                                || hasTransport( NetworkCapabilities.TRANSPORT_CELLULAR )
                                || hasTransport( NetworkCapabilities.TRANSPORT_VPN )
                    }
                }
                else {
                    connectivityManager.getNetworkCapabilities( network )?.run {
                        return hasTransport( NetworkCapabilities.TRANSPORT_WIFI )
                                || hasTransport( NetworkCapabilities.TRANSPORT_CELLULAR )
                                || hasTransport( NetworkCapabilities.TRANSPORT_VPN )
                    }
                }
            }
            else {
                return connectivityManager.activeNetworkInfo!!.isConnected
            }
            return true
        }

        fun registerConnectivityChangeEvents( context: Context ) {
            val connectivityManager = context.getSystemService( Context.CONNECTIVITY_SERVICE ) as ConnectivityManager
            val networkCallback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isConnected.value = isConnectedWithNetwork( context , network )
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    isConnected.value = isConnectedWithNetwork( context , network )
                }

            }
            val networkRequest = NetworkRequest.Builder().run {
                addCapability( NetworkCapabilities.NET_CAPABILITY_INTERNET )
                build()
            }
            // TODO : Handle API 25 case here
            connectivityManager.registerNetworkCallback( networkRequest , networkCallback )
        }

    }


}