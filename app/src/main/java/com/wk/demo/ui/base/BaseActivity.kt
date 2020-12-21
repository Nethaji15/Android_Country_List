package com.wk.demo.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.wk.demo.R
import com.wk.demo.utils.Constants
import dagger.android.AndroidInjection
import timber.log.Timber

/**
 * Base Activity to have all the common functionality for all the activity in the application
 */
open class BaseActivity() : AppCompatActivity() {

    private lateinit var permissionListener: PermissionRequestListener
    lateinit var netmonitor: NetStatusReceiver
    lateinit var netIntent: IntentFilter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        //initializing the network monitor
        netmonitor = NetStatusReceiver()
        netIntent = IntentFilter()
        netIntent.addAction("android.net.conn.CONNECTIVITY_CHANGE")
    }

    override fun onResume() {
        super.onResume()

        // registering the network monitor
        registerReceiver(netmonitor, netIntent)
    }

    override fun onPause() {
        super.onPause()


        Timber.i("paused App")
        // unregistering the network monitor
        unregisterReceiver(netmonitor)


    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("Destroyed App")
    }



    // Snack bar on top to notice internet availability
    open fun showConnectionStatus(status: Boolean) {
        var msg =
            if (status) getString(R.string.net_available) else getString(R.string.net_not_available)
        val snack = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
        val view = snack?.view
        val params = view?.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        if (status)
            view.setBackgroundColor(Color.GREEN)
        else
            view.setBackgroundColor(Color.RED)
        snack.show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_REQUSET_CODE) {
            permissionListener?.permissionResponse(permissions, grantResults)
        }
    }

    open var isConnected: Boolean = false

    /**
     * Broadcast receiver to monitor network availability
     */
    class NetStatusReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val conectivity =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = conectivity.activeNetworkInfo
            var isConnected = activeNetwork?.isConnected == true
            (context as BaseActivity).isConnected = isConnected
            if (!isConnected)
                (context).showConnectionStatus(isConnected)
        }

    }

}

 interface PermissionRequestListener {
    fun permissionResponse(permissions: Array<out String>, grantResults: IntArray)
}