package com.example.bloodapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.bloodapp.MainActivity;
import com.example.bloodapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(Splashscreen.this, LoginActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(Splashscreen.this, MainActivity.class));
                }
            }
        },4000);
    }
}