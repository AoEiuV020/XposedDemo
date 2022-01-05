package cc.aoeiuv020.xposed.demo

import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class AccessibilityActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AccessibilityActivity::class.java)
            context.startActivity(starter)
        }

        private const val TAG = "AccessibilityActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessibility)

        checkDisabled()
    }

    @SuppressLint("SetTextI18n")
    private fun checkDisabled() {
        val tvResult: TextView = findViewById(R.id.tvResult)
        val tvReason: TextView = findViewById(R.id.tvReason)

            val accessibilityManager =
            ContextCompat.getSystemService(this, AccessibilityManager::class.java)
                ?: error("unreachable")
        val serviceList: List<AccessibilityServiceInfo> =
            accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        Log.d(TAG, "enabled accessibility service: $serviceList")
        if (serviceList.isNotEmpty()) {
            tvResult.text = "你是残疾人！"
            tvReason.text = "因为你正在使用：\n" + serviceList.joinToString("\n") {
                packageManager.getApplicationLabel(it.resolveInfo.serviceInfo.applicationInfo)
            }
        } else {
            tvResult.text = "你很健康"
            tvReason.text = ""
        }
    }

}