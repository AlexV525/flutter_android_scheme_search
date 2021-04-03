package com.alexv525.android_scheme_search

import androidx.annotation.NonNull

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.*
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class AndroidSchemeSearchPlugin: FlutterPlugin, MethodCallHandler {
  private var applicationContext: Context? = null

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    applicationContext = flutterPluginBinding.applicationContext
    onAttachedToEngine(flutterPluginBinding.binaryMessenger, null)
  }

  private fun onAttachedToEngine(messenger: BinaryMessenger, context: Context?) {
    val methodChannel = MethodChannel(messenger, channelName)
    methodChannel.setMethodCallHandler(this)
    if (context != null) applicationContext = context
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    val url = call.argument<String>("url")
    when (call.method) {
      "search" -> result.success(search(url))
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    applicationContext = null
  }

  private fun search(url: String?): String? {
    val intent = Intent(Intent.ACTION_VIEW).apply {
      data = Uri.parse(url)
    }
    val packageManager = applicationContext!!.packageManager
    val componentName = intent.resolveActivity(packageManager)
    return if (componentName != null) {
      try {
        val packageInfo = packageManager.getPackageInfo(componentName.packageName, 0)
        packageManager.getApplicationLabel(packageInfo.applicationInfo) as String
      } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        e.toString()
      }
    } else {
      null
    }
  }

  companion object {
    private const val channelName = "android_scheme_search"
  }
}
