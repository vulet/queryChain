package com.querychain.mainapp;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;


public class LibraryDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CertificationImageAdapter adapter;
    private List<Certifications> certificationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.librarymain);
        recyclerView = findViewById(R.id.recycler_view);
        certificationsList = new ArrayList<>();
        adapter = new CertificationImageAdapter(this, certificationsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareCertifications();
    }

    private void prepareCertifications() {
        DataBaseHandler dbs = new DataBaseHandler(this);
        List<Certifications> certifications = dbs.getAllCertifications();
        for (Certifications cn : certifications) {
            String log = "ID:" + cn.getId() + " Name: " + cn.getName()
                    + " ,Image: " + cn.getImage();
            certificationsList.add(cn);
            adapter.notifyDataSetChanged();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        View view = bottomNavigationView.findViewById(R.id.menu_library);
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
                        Intent launchMainMenu = new Intent(LibraryDisplay.this, MainMenu.class);
                        launchMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchMainMenu);
                    }

                    private void startCameraHome() {
                        Intent launchCameraHome = new Intent(LibraryDisplay.this, MainActivity.class);
                        launchCameraHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchCameraHome);
                    }

                    private void startLibrary() {
                        Intent launchMainMenu = new Intent(LibraryDisplay.this, MainMenu.class);
                        launchMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                });
    }
}
