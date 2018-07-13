package com.querychain.mainapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    private static final String MODEL_PATH = "graph.lite";
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult;
    private TextView textViewResult2;
    private Button image_query, detect_object;
    private CameraView cameraView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.cameraView);
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult2 = findViewById(R.id.textViewResult2);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());
        detect_object = findViewById(R.id.detect_object);
        image_query = findViewById(R.id.image_query);
        final DataBaseHandler dbs = new DataBaseHandler(this);

        cameraView.addCameraKitListener(new CameraKitEventListener() {

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                cameraView.stop();
                final List < Classifier.Recognition > results = classifier.recognizeImage(bitmap);
                cameraView.start();
                Iterator < Classifier.Recognition > itr = results.iterator();
                itr.next();
                String certName = results.get(0).getId();
                SQLiteDatabase db = dbs.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * from certs where ID =" + certName, null);
                DatabaseUtils.dumpCursor(cursor);
                textViewResult.setText(results.toString());
                if (results.size() > 0)
                    textViewResult2.setText(results.get(0).getId());
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }

            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }
        });

        detect_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
                if (image_query.getVisibility() == View.INVISIBLE) {
                    image_query.setVisibility(View.VISIBLE);
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        View view = bottomNavigationView.findViewById(R.id.menu_camera);
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
                        Intent launchMainMenu = new Intent(MainActivity.this, MainMenu.class);
                        launchMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchMainMenu);
                    }

                    private void startCameraHome() {
                        Intent launchCameraHome = new Intent(MainActivity.this, MainActivity.class);
                        launchCameraHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchCameraHome);
                    }

                    private void startLibrary() {
                        Intent launchLibrary = new Intent(MainActivity.this, LibraryDisplay.class);
                        launchLibrary.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(launchLibrary);
                    }
                });

        image_query.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewNote.class);
                if (textViewResult2.getText() != null) {
                    intent.putExtra("dbKey", textViewResult2.getText());
                    startActivity(intent);
                }
            }
        });

        initTensorFlowAndLoadModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                image_query.setVisibility(View.INVISIBLE);
                detect_object.setVisibility(View.VISIBLE);
            }
        });
    }
}

