package com.service.avishai.cameratrystuff2;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        b = (Button)findViewById(R.id.button);

        requestFewPermissions(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void requestFewPermissions(String... permissions) {
        requestPermissions(permissions,19999);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 19999) {
            Log.d("tag", "onRequestPermissionsResult: ");
        }
    }

    public void moveToNextActivity(View view){

        Intent intent = new Intent(this, SeondActivity.class);
        startActivity(intent);
    }
}
