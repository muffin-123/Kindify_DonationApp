package com.example.tawfiqthefooddonationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    ImageView fb, linkedln, twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        fb = findViewById(R.id.imgFb);
        linkedln = findViewById(R.id.imgLink);
        twitter = findViewById(R.id.imgTwt);


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myWeblink = new Intent(Intent.ACTION_VIEW);
                myWeblink.setData(Uri.parse("https://www.facebook.com/profile.php?id=100073413510094&mibextid=ZbWKwL"));
                startActivity(myWeblink);
            }
        });

        linkedln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myWeblink = new Intent(Intent.ACTION_VIEW);
                myWeblink.setData(Uri.parse("https://www.linkedin.com/in/rooha-tanveer-220476282?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"));
                startActivity(myWeblink);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myWeblink = new Intent(Intent.ACTION_VIEW);
                myWeblink.setData(Uri.parse("https://x.com/Rooha83137127?t=LhE01wpVOjBJL0HKMRJFpg&s=08 "));
                startActivity(myWeblink);
            }
        });
    }
}