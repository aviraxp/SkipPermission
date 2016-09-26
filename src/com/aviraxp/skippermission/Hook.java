package com.aviraxp.skippermission;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook implements IXposedHookZygoteInit, IXposedHookLoadPackage {

    public XC_MethodHook grantPermissionsBackButtonHook;

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {

        grantPermissionsBackButtonHook = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                //setResultAndFinish()
                XposedHelpers.callMethod(param.thisObject, "setResultAndFinish");
            }
        };
    }

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.android.packageinstaller") || lpparam.packageName.equals("com.google.android.packageinstaller") || lpparam.packageName.equals("com.mokee.packageinstaller") || lpparam.packageName.equals("com.samsung.android.packageinstaller")) {
            XposedHelpers.findAndHookMethod("com.android.packageinstaller.permission.ui.GrantPermissionsActivity",
                    lpparam.classLoader, "onCreate", grantPermissionsBackButtonHook);
        }
    }
}
