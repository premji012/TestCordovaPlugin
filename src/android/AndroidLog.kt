package org.apache.cordova.log

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
        val message = args.getString(0)
        if (action == "loadModule") {
            loadModule(message, callbackContext)
            return true
        } else if (action == "pageLoaded") {
            handlePageLoadedMsg(message, callbackContext)
        }
        return false
    }

    private fun loadModule(message: String?, callbackContext: CallbackContext) {
        if (!message.isNullOrEmpty()) {
            val activity = cordova.activity as MultiViewActivity
            activity.loadNewWebView("file:///android_asset/www/test.html")
            callbackContext.success(message)
        } else {
            callbackContext.error("Expected one non-empty string argument.")
        }
    }

    private fun handlePageLoadedMsg(message: String?, callbackContext: CallbackContext) {
        if (!message.isNullOrEmpty()) {
            val activity = cordova.activity as MultiViewActivity
            activity.performViewtransition()
            callbackContext.success(message)
        } else {
            callbackContext.error("Expected one non-empty string argument.")
        }
    }
}