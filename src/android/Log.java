package org.apache.cordova.log;

import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class Log extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("printLog")) {
            String message = args.getString(0);
            this.printLog(message, callbackContext);
            return true;
        }
        return false;
    }

    private void printLog(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            Log.d("printNative", message);
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
