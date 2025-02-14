package com.example.blockweb

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.content.Context
import android.content.Context.ACCESSIBILITY_SERVICE
import android.content.Intent
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.core.content.ContextCompat.startActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MethodChannelManager(private val context: Context, private val activity: Activity, flutterEngine: FlutterEngine) {
    private val CHANNEL = "web_blocker"


    init {
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "enableAccessibilityService" -> {
                    openAccessibilitySettings()
                    result.success("Opened Accessibility Settings")
                }
                else -> result.notImplemented()
            }
        }

    }
    private fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        context.startActivity(intent)
    }
    fun isAccessibilityServiceEnabled(
        context: Context,
        service: Class<out AccessibilityService?>
    ): Boolean {
        val am = context.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices =
            am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        for (enabledService in enabledServices) {
            val enabledServiceInfo = enabledService.resolveInfo.serviceInfo
            if (enabledServiceInfo.packageName == context.packageName && enabledServiceInfo.name == service.name) return true
        }
        return false
    }

}