package com.example.devicepolicymanagerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
    }

    public void OnActivate(View v) {
        DeviceManagerMethod.getInstance(mContext).onActivate();}
    public void OnRemoveActivate(View v) {
        DeviceManagerMethod.getInstance(mContext).onRemoveActivate();}
    public void btnLockMethod(View v){
        DeviceManagerMethod.getInstance(mContext).startLockMethod();}
    public void btnLock(View v){
        DeviceManagerMethod.getInstance(mContext).LockNow();}
    public void btnWipe(View v){
        DeviceManagerMethod.getInstance(mContext).WipeData();}

    //模拟View的点击事件
    private void setSimulateClick(View v) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, 0, 0, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, 0, 0, 0);
        v.onTouchEvent(downEvent);
        v.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }
}
