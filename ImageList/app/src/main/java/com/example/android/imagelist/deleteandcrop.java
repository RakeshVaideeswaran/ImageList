package com.example.android.imagelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import static android.R.attr.delay;
import static android.os.Build.VERSION_CODES.M;
import static com.example.android.imagelist.MyAdapter.LIST;

public class deleteandcrop extends AppCompatActivity {

    ImageList temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteandcrop);

    }

    public void del(View view)
    {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

}
