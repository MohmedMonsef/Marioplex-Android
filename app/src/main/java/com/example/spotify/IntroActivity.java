package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void LaunchLoginActivity(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void LaunchSignUpActivity(View view) {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}
