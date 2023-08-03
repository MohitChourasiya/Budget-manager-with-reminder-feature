package com.example.budgetmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Splash extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser()!=null){
                    try {
                        new Handler(Looper.getMainLooper()).

                                postDelayed(new Runnable() {
                                    @Override
                                    public void run () {
                                            Intent mainIntent = new Intent(Splash.this,Dashboard.class);
                                            startActivity(mainIntent);
                                            finish();

                                    }
                                },3000);

                    }catch (Exception e){

                    }
                }else{
                    new Handler(Looper.getMainLooper()).

                            postDelayed(new Runnable() {
                                @Override
                                public void run () {
                                    startActivity(new Intent(Splash.this,Login.class));
                                    finish();

                                }
                            },3000);

                }
            }
        });

    }

}

