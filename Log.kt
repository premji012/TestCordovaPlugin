package org.apache.cordova.log

import android.util.Log
import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaPlugin
import org.json.JSONArray
import org.json.JSONException

/**
 * This class echoes a string called from JavaScript.
 */
class Log : CordovaPlugin() {
    @Throws(JSONException::class)
    fun execute(action: String, args: JSONArray, callbackContext: CallbackContext): Boolean {
        if (action == "printLog") {
            val message = args.getString(0)
            printLog(message, callbackContext)
            return true
        }
        return false
    }

    private fun printLog(message: String?, callbackContext: CallbackContext) {
        if (message != null && message.length > 0) {
            Log.d("Testing cordova plugin", message)
            callbackContext.success(message)
        } else {
            callbackContext.error("Expected one non-empty string argument.")
        }
    }
}