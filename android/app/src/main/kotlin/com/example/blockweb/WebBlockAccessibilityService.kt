package com.example.blockweb


import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

class WebBlockAccessibilityService : AccessibilityService() {

    private val blockedUrls = setOf("example.com", "facebook.com", "tiktok.com")

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.eventType != AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) return

        val rootNode = rootInActiveWindow ?: return
        checkAndBlockWebsite(rootNode)
    }

    private fun checkAndBlockWebsite(node: AccessibilityNodeInfo?) {
        if (node == null) return

        if (node.className == "android.widget.EditText") {
            val text = node.text?.toString() ?: return
            for (blockedUrl in blockedUrls) {
                if (text.contains(blockedUrl)) {
                    blockWebsite()
                    break
                }
            }
        }

        for (i in 0 until node.childCount) {
            checkAndBlockWebsite(node.getChild(i))
        }
    }

    private fun blockWebsite() {
        performGlobalAction(GLOBAL_ACTION_BACK)
        Log.d("WebBlockService", "Blocked access to a restricted website")
    }

    override fun onInterrupt() {
        Log.d("WebBlockService", "Accessibility Service Interrupted")
    }
}
