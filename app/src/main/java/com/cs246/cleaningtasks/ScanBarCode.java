package com.cs246.cleaningtasks;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * <h1>SCANBARCODE</h1>
 * <p>Handles all the logic for scanning barcodes/QR and other
 * 1d and 2d scannable codes supported by Google's Mobile Vision API</p>
 */
public class ScanBarCode extends AppCompatActivity {
    SurfaceView cameraPreview;

    /**
     * <h2>ScanBarCode onCreate</h2>
     * <p>Sets the Widgets and creates the camera source</p>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_scanner_layout);

        cameraPreview = (SurfaceView) findViewById(R.id.camera_preview);

        createCameraSource();
    }

    /**
     * <h2>ScanBarCode createCameraSource</h2>
     * <p>Creates and sets all image processing by the camera</p>
     */
    private void createCameraSource() {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();

        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        //Starts the camera preview in the layout
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                //permissions were requested in the calling activity because when granted in this
                //   activity, the activity had to be exited and reentered for camera preview to work
                if (ActivityCompat.checkSelfPermission(ScanBarCode.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                //try to start the camera
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                //intentionally blank: automatically generated
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        //Sets the image processor for the barcodes
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //intentionally blank: automatically generated
            }

            @Override
            //Receives the barcode information from the camera
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size() > 0){
                    Intent intent = new Intent();
                    //get only latest code
                    intent.putExtra("barcode", barcodes.valueAt(0));

                    //sets the results to be received in TaskBoard.class
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    finish();
                }
            }
        });
    }
}
