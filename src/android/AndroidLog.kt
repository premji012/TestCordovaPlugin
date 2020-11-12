package org.apache.cordova.log

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.cordova.hellocordova.MainActivity
import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaPlugin
import org.json.JSONArray
import org.json.JSONException

/**
 * This class echoes a string called from JavaScript.
 */
class AndroidLog : CordovaPlugin() {
    @Throws(JSONException::class)
    override fun execute(action: String, args: JSONArray, callbackContext: CallbackContext): Boolean {
        if (action == "loadModule") {
            val message = args.getString(0)
            loadModule(message, callbackContext)
            return true
        } else if (action == "dismissModule") {
            cordova.activity.finish()
        }
        return false
    }

    private fun destroyWebView(broadcastReceiver: BroadcastReceiver) {
        cordova.activity.unregisterReceiver(broadcastReceiver)
        cordova.activity.finish()
    }

    private fun loadModule(message: String?, callbackContext: CallbackContext) {
        if (message != null && message.length > 0) {
            val activity = cordova.activity as MainActivity
            val intent = Intent(activity, MultiViewActivity::class.java).apply {
                putExtra("url", "file:///android_asset/www/test.html")
            }
            val moduleToModuleReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val message = intent!!.getStringExtra("message")
                    if (message == "PAGE_LOADED") {

                    }
                }

            }
            cordova.startActivityForResult(this, intent, 987)
            Log.d("printNative", message)
            callbackContext.success(message)
        } else {
            callbackContext.error("Expected one non-empty string argument.")
        }
    }
}