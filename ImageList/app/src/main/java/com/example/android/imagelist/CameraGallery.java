package com.example.android.imagelist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CameraGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_gallery);
    }

    public void camera(View view)
    {
        Intent i = new Intent();
        i.putExtra("camgalres",100);
        setResult(RESULT_OK, i);
        finish();
    }

    public void gallery(View view)
    {
        Intent i = new Intent();
        i.putExtra("camgalres",200);
        setResult(RESULT_OK,i);
        finish();
    }

}
