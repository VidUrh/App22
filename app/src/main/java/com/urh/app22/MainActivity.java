package com.urh.app22;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStuff();
            }
        });
    }

    private void doStuff() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);

            return;
        }


        Bitmap b = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        c.drawRect(0, 0, 640, 480, p);
        p.setColor(Color.WHITE);
        p.setTextSize(48);

        String tekst = ((EditText) findViewById(R.id.editText)).getText().toString();

        int x = (int) ((b.getWidth() / 2)
                - (p.measureText(tekst) / 2));
        int y = (int) ((b.getWidth() / 2)
                - ((p.descent() + p.ascent()) / 2));

        c.drawText(tekst, x, y, p);

        File f = new  File(Environment.getExternalStorageDirectory(), "pic.jpg");

        try {

            FileOutputStream out = new FileOutputStream(f);
            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        }catch (Exception e) {}

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123){
            if (grantResults.length > 0
                    && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                doStuff();
            }
            return;
        }
    }
}
