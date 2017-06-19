package com.example.android.imagelist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.android.imagelist.MyAdapter.LIST;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    Button Add;
    final int captionconstant = 0;
    final int dialog = 1;
    final int camera = 2;
    final int gallery =3;
    final int cameragallery = 4;
    final int def = 5;
    Button Delete,Crop;
    String msgfn;
    String urifn;
    ListView L;
    int camgalresult;
    public static MyAdapter adapter;
    public int p;

        Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        L = (ListView) findViewById(R.id.listview);
        Add = (Button) findViewById(R.id.addbutton);
        Delete = (Button) findViewById(R.id.deletetbutton);
        Crop = (Button) findViewById(R.id.cropbutton);


        adapter = new MyAdapter(this);

        L.setAdapter(adapter);
        L.setOnItemClickListener(this);



        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this,CameraGallery.class);
                startActivityForResult(in,cameragallery);

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == cameragallery)
        {

            camgalresult = data.getIntExtra("camgalres",def);
            if(resultCode == RESULT_OK)
            {
                if(camgalresult == 100)
                {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, camera);
                }

                else if(camgalresult == 200)
                {
                    Intent gallintent = new Intent(Intent.ACTION_PICK);
                    File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String s = f.getPath();
                    Uri u = Uri.parse(s);
                    gallintent.setDataAndType(u,"image/*");
                    startActivityForResult(gallintent,gallery);
                }
            }
        }

        if(requestCode == dialog)
        {
            if(resultCode == RESULT_OK)
            {
                LIST.remove(p);
                adapter.notifyDataSetChanged();
            }
        }

        else if(requestCode==camera)
        {
            if(resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                urifn = uri.toString();

                photo = (Bitmap) data.getExtras().get("data");
               Intenttocap();
            }

        }

        else if(requestCode == gallery)
        {
            if(resultCode == RESULT_OK)
            {
                Uri imguri = data.getData();

                urifn = imguri.toString();

                try
                {
                   photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imguri);
                }
                catch (IOException e) {e.printStackTrace();}

                Intenttocap();
            }
        }

        else if(requestCode == captionconstant)
        {
            if(resultCode == RESULT_OK)
            {
                String msg = data.getStringExtra("captionmessage");
                ImageList t = new ImageList(photo, msg);
                LIST.add(t);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(MainActivity.this, deleteandcrop.class);
        startActivityForResult(i,1);
        p=position;

    }

    public void Intenttocap()
    {
        Intent intenttocap = new Intent(MainActivity.this,CaptionActivity.class);
        startActivityForResult(intenttocap,captionconstant);
    }

}


class ImageList
{
    Bitmap img;
    String cap;

    ImageList(Bitmap image,String cap)
    {
        this.img = image;
        this.cap = cap;
    }
}


class MyAdapter extends BaseAdapter
{
    Context c;
    public static ArrayList<ImageList> LIST;

    MyAdapter(Context context)
    {
        this.c = context;
        LIST = new ArrayList<ImageList>();

    }


    @Override
    public int getCount() {
        return LIST.size();
    }

    @Override
    public Object getItem(int position) {
        return LIST.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        MyViewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.singlerow,parent,false);

            holder = new MyViewHolder(row);
            row.setTag(holder);
        }

        else
        {
            holder = (MyViewHolder)row.getTag();
        }

        ImageList temporary = LIST.get(position);

        holder.T.setText(temporary.cap);
        holder.I.setImageBitmap(temporary.img);



        return row;
    }
}


class MyViewHolder{

    ImageView I;
    TextView T;

    MyViewHolder(View v)
    {
        I = (ImageView) v.findViewById(R.id.imgview);
        T = (TextView) v.findViewById(R.id.textview);
    }
}

