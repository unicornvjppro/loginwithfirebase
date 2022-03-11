package com.example.logintest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("remember","false");
        editor.apply();
        finish();
    }
}