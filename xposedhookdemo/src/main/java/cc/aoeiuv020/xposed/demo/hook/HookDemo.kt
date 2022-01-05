package cc.aoeiuv020.xposed.demo.hook

import android.accessibilityservice.AccessibilityServiceInfo
import de.robv.android.xposed.*
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class HookDemo : IXposedHookLoadPackage, IXposedHookZygoteInit {
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        XposedBridge.log("Loaded app: " + lpparam.packageName)
        XposedHelpers.findAndHookMethod(
            "cc.aoeiuv020.xposed.demo.MainActivity",
            lpparam.classLoader, "getGreetingWord", XC_MethodReplacement.returnConstant("你马死了！")
        )
    }

    @Throws(Throwable::class)
    override fun initZygote(startupParam: StartupParam) {
        XposedBridge.log("initZygote: " + startupParam.modulePath)
        XposedHelpers.findAndHookMethod(
            "android.view.accessibility.AccessibilityManager",
            null,
            "getEnabledAccessibilityServiceList",
            Int::class.javaPrimitiveType,
            XC_MethodReplacement.returnConstant(emptyList<AccessibilityServiceInfo>())
        )
    }
}