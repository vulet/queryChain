package com.querychain.mainapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewNote extends AppCompatActivity {


    private TextView textViewName;
    private TextView textViewDescription;
    private TextView certURL;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final DataBaseHandler dbs = new DataBaseHandler(this);
        final ImageView logoView = findViewById(R.id.imageViewLogo);
        textViewName = findViewById(R.id.textViewName);
        textViewDescription = findViewById(R.id.textViewDescription);
        certURL = findViewById(R.id.certURL);
        Button readMore = findViewById(R.id.readMore);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        View view = bottomNavigationView.findViewById(R.id.menu_library);
        textViewDescription.setMovementMethod(new ScrollingMovementMethod());
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
                        Intent launchMainMenu = new Intent(ViewNote.this, MainMenu.class);
                        launchMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchMainMenu);
                    }

                    private void startCameraHome() {
                        Intent launchCameraHome = new Intent(ViewNote.this, MainActivity.class);
                        launchCameraHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchCameraHome);
                    }

                    private void startLibrary() {
                        Intent launchLibrary = new Intent(ViewNote.this, LibraryDisplay.class);
                        launchLibrary.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchLibrary);

                    }

                });

        Bundle extras = getIntent().getExtras();
        // get data via dbKey
        String value1 = extras.getString("dbKey");
        if (value1 != null && !value1.isEmpty()) {
            SQLiteDatabase db = dbs.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * from certs where ID =" + value1, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        byte[] temp_image = cursor.getBlob(3);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(temp_image, 0, temp_image.length);
                        logoView.setImageBitmap(bitmap);
                        textViewName.setText(cursor.getString(1));
                        textViewDescription.setText(cursor.getString(2));
                        certURL.setText(cursor.getString(4));
                    }
                    while (cursor.moveToNext());
                }
            }
            cursor.close();
        }

        readMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i(); }
        });
    }

    private void i() {
        String url = "https://querychain.com/";
        String certUrl = certURL.getText().toString();
        String html = ".html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url + certUrl + html));
        startActivity(i);
    }
}





