package com.example.devicepolicymanagerdemo;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.devicepolicymanagerdemo.receiver.DeviceManagerReceiver;

public class DeviceManagerMethod {
    private final String TAG = "DeviceManagerMethod";
    private static DeviceManagerMethod mDeviceMethod;

    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;
    private Context mContext;

    private DeviceManagerMethod(Context context){
        mContext=context;
        //获取设备管理服务
        devicePolicyManager=(DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        //DeviceManagerReceiver 继承自 DeviceAdminReceiver
        componentName=new ComponentName(context, DeviceManagerReceiver.class);
    }

    /**
     * 单例创建
     */
    public static DeviceManagerMethod getInstance(Context context){
        if (mDeviceMethod==null) {
            synchronized (DeviceManagerMethod.class) {
                if (mDeviceMethod==null) {
                    mDeviceMethod=new DeviceManagerMethod(context);
                }
            }
        }
        return mDeviceMethod;
    }

    // 激活程序
    public void onActivate() {
        Log.d(TAG, "onActivate: 激活中...");
        if (!devicePolicyManager.isAdminActive(componentName)) {
            Intent intent = new Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }else {
            Log.d(TAG, "onActivate: 设备已经激活");
        }
    }

    /**
     * 移除程序
     * 如果不移除程序 似乎APP无法被卸载，未验证
     */
    public void onRemoveActivate() {
        devicePolicyManager.removeActiveAdmin(componentName);

    }

    /**
     * 设置解锁方式 此API不需要激活就可以运行
     */
    public void startLockMethod() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 锁屏
     */
    public void LockNow() {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.lockNow();
        }else {
            Log.d(TAG, "LockNow: 请先激活");
        }
    }

    /**
     * 设置多长时间后锁屏
     * @param time
     */
    public void LockByTime(long time) {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.setMaximumTimeToLock(componentName, time);
        }else {
            Log.d(TAG, "LockByTime: 请先激活");
        }
    }

    /**
     * 恢复出厂设置
     */
    public void WipeData() {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
        }else {
            Log.d(TAG, "WipeData: 请先激活");
        }
    }
}
