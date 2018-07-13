package com.querychain.mainapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ViewReadMe extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readme_main);
        final DataBaseHandler dbs = new DataBaseHandler(this);
        Button textViewRead = findViewById(R.id.textViewRead);
        TextView textViewReadDescription;
        textViewReadDescription = findViewById(R.id.textViewReadDescription);
        textViewReadDescription.setMovementMethod(new ScrollingMovementMethod());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        View view = bottomNavigationView.findViewById(R.id.menu_home);
        view.performClick();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.menu_home:
                                startMainMenu();
                                break;
                            case R.id.menu_camera:
                                startCameraHome();
                                break;
                            case R.id.menu_library:
                                startLibrary();
                        }
                        return true;
                    }

                    private void startMainMenu() {
                        Intent launchMainMenu = new Intent(ViewReadMe.this, MainMenu.class);
                        launchMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchMainMenu);
                    }

                    private void startCameraHome() {
                        Intent launchCameraHome = new Intent(ViewReadMe.this, MainActivity.class);
                        launchCameraHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchCameraHome);
                    }

                    private void startLibrary() {
                        Intent launchLibrary = new Intent(ViewReadMe.this, LibraryDisplay.class);
                        launchLibrary.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchLibrary);

                    }
                });

        textViewRead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i();
            }
        });
    }

        private void i() {
            String url = "https://querychain.com/privacypolicy.html";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
    }
}

