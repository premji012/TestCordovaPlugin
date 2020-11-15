/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package org.apache.cordova.log

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import org.apache.cordova.CordovaActivity
import org.apache.cordova.CordovaInterface
import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CordovaWebView
import java.util.concurrent.ExecutorService

class MultiViewActivity : CordovaActivity(), CordovaInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enable Cordova apps to be started in the background
        val extras = intent.extras
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true)
        }

        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl)
    }

    @SuppressLint("ResourceType")
    override fun createViews() {
        appView.view.id = 100
        appView.view.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val root = FrameLayout(this)
        root.addView(appView.view, 0)
        setContentView(root)
        if (preferences.contains("BackgroundColor")) {
            try {
                val backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK)
                // Background of activity:
                appView.view.setBackgroundColor(backgroundColor)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
        appView.view.requestFocusFromTouch()
    }

    @SuppressLint("ResourceType")
    fun addNewView(nextView: CordovaWebView) {
        val view = nextView.view
        view.id = 101
        view.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.visibility = View.INVISIBLE
        val root = findViewById<View>(100).rootView as ViewGroup
        root.addView(view, 1)
    }

    fun loadNewWebView(url: String?) {
        val nextView = makeWebView()
        addNewView(nextView)
        if (!nextView.isInitialized) {
            nextView.init(cordovaInterface, pluginEntries, preferences)
        }
        cordovaInterface.onCordovaInit(nextView.pluginManager)
        nextView.loadUrlIntoView(url, true)
    }

    @SuppressLint("ResourceType")
    fun performViewtransition() {
        val newView = findViewById<View>(101)
        val oldView = findViewById<View>(100)
        newView.alpha = 0f
        newView.visibility = View.VISIBLE
        oldView.alpha = 1f
        oldView.animate().alpha(0f).duration = 1500
        newView.animate().alpha(1f).duration = 1500
    }

    override fun startActivityForResult(command: CordovaPlugin, intent: Intent, requestCode: Int) {}
    override fun setActivityResultCallback(plugin: CordovaPlugin) {}
    override fun getActivity(): Activity {
        return this
    }

    override fun getContext(): Context? {
        return null
    }

    override fun getThreadPool(): ExecutorService? {
        return null
    }

    override fun requestPermission(plugin: CordovaPlugin, requestCode: Int, permission: String) {}
    override fun requestPermissions(
        plugin: CordovaPlugin,
        requestCode: Int,
        permissions: Array<String>
    ) {
    }

    override fun hasPermission(permission: String): Boolean {
        return false
    }
}