package com.example.blockweb

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity() {
    private lateinit var methodChannelManager: MethodChannelManager



    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        methodChannelManager = MethodChannelManager(this,activity, flutterEngine)
    }
}