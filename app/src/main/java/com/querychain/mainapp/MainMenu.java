package com.querychain.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainMenu extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        Button LibraryHomeButton = findViewById(R.id.LibraryHomeBtn);
        Button CameraHomeButton = findViewById(R.id.CameraHomeButton);
        Button ReadMeButton = findViewById(R.id.ReadMeButton);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                break;
                            case R.id.menu_camera:
                                startCameraHome();
                                break;
                            case R.id.menu_library:
                            startLibrary();
                        }
                        return true;
                    }
                });

        LibraryHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startLibrary();
            }
        });

        CameraHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startCameraHome();
            }
        });

        ReadMeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startReadMe();
            }
        });
    }

    private void startLibrary() {
        Intent launchLibrary = new Intent(this, LibraryDisplay.class);
        startActivity(launchLibrary);
    }

    private void startCameraHome() {
        Intent launchCameraHome = new Intent(this, MainActivity.class);
        startActivity(launchCameraHome);
    }

    private void startReadMe() {
        Intent launchReadMe = new Intent(this, ViewReadMe.class);
        startActivity(launchReadMe);
    }
}
