package com.example.android.imagelist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CaptionActivity extends AppCompatActivity {

    EditText E;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption);
        E= (EditText) findViewById(R.id.capbox);
    }

    public void send(View view)
    {
        String message = E.getText().toString();
        Intent i = new Intent();
        i.putExtra("captionmessage",message);
        setResult(RESULT_OK,i);
        finish();
    }

}
