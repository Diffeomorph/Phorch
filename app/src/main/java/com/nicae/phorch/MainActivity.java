package com.nicae.phorch;

import android.app.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;


public class MainActivity extends Activity {

    public RelativeLayout container;
    private boolean isLighOn = false;
    private Camera camera;

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;
        PackageManager pm = context.getPackageManager();

        // if device support camera?
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.e("err", "Device has no camera!");
            return;
        }

        camera = Camera.open();

        container = (RelativeLayout) findViewById(R.id.MainActivity);
        container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeScreen(v);
                toggleLight();
            }
        });
    }

    public void changeScreen(View v) {
        ColorDrawable cd = (ColorDrawable) this.container.getBackground();
        TextView OFF = (TextView) findViewById(R.id.OFF);
        TextView ON = (TextView) findViewById(R.id.ON);

        if (cd != null && cd.getColor() == getResources().getColor(R.color.BLACK)) {
            container.setBackgroundColor(getResources().getColor(R.color.WHITE));
            OFF.setVisibility(View.INVISIBLE);
            ON.setVisibility(View.VISIBLE);
        } else {
            container.setBackgroundColor(getResources().getColor(R.color.BLACK));
            OFF.setVisibility(View.VISIBLE);
            ON.setVisibility(View.INVISIBLE);
        }
    }

    public void toggleLight(){
        final Camera.Parameters p = camera.getParameters();

        if (isLighOn) {

            Log.i("info", "torch is turn off!");

            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
            isLighOn = false;

        } else {

            Log.i("info", "torch is turn on!");

            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

            camera.setParameters(p);
            camera.startPreview();
            isLighOn = true;


        }
    }

}