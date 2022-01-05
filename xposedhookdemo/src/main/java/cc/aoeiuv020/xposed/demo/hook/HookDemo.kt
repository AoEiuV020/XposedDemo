package cc.aoeiuv020.xposed.demo.hook

import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class HookDemo : IXposedHookLoadPackage {
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        XposedBridge.log("Loaded app: " + lpparam.packageName)
        XposedHelpers.findAndHookMethod("cc.aoeiuv020.xposed.demo.MainActivity",
        lpparam.classLoader, "getGreetingWord", XC_MethodReplacement.returnConstant("你马死了！"))
    }
}