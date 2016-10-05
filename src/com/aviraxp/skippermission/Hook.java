package com.aviraxp.skippermission;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.android.packageinstaller")) {
                XposedBridge.log("Load Packageinstaller");
                XposedHelpers.findAndHookMethod("com.android.packageinstaller.permission.ui.GrantPermissionsActivity", lpparam.classLoader, "onCreate", android.os.Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedHelpers.callMethod(param.thisObject, "setResultAndFinish");
                    }
                });
        }
    }
}