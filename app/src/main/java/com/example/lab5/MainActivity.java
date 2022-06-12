package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String COLOR_RED = "#FF0000";
    private static final String COLOR_ORANGE = "#FFBB00";
    private static final String COLOR_BLUE = "#00FFFF";
    private static final String COLOR_GREEN = "#00FF00";

    private DrawView mDrawView;
    private Button mButtonColorRed;
    private Button mButtonColorOrange;
    private Button mButtonColorBlue;
    private Button mButtonColorGreen;
    private Button mButtonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectFieldsWithLayoutElements();
        configureDrawView();
        configureButtons();
    }

    private void connectFieldsWithLayoutElements() {
        mDrawView = findViewById(R.id.drawingSurface);
        mButtonColorRed = findViewById(R.id.buttonColorRed);
        mButtonColorOrange = findViewById(R.id.buttonColorOrange);
        mButtonColorBlue = findViewById(R.id.buttonColorBlue);
        mButtonColorGreen = findViewById(R.id.buttonColorGreen);
        mButtonClear = findViewById(R.id.buttonClear);
    }

    private void configureDrawView() {
        mDrawView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mDrawView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = mDrawView.getHeight();
                int width = mDrawView.getWidth();
                mDrawView.init(height, width);
            }
        });
    }

    private void configureButtons() {
        setColorForButton(mButtonColorRed, COLOR_RED);
        setColorForButton(mButtonColorOrange, COLOR_ORANGE);
        setColorForButton(mButtonColorBlue, COLOR_BLUE);
        setColorForButton(mButtonColorGreen, COLOR_GREEN);
        setClearDrawing();
    }

    private void setColorForButton(Button button, String colorInHex) {
        button.setOnClickListener(view ->
                mDrawView.setColor(Color.parseColor(colorInHex)));
    }

    private void setClearDrawing() {
        mButtonClear.setOnClickListener(view -> mDrawView.clear());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_image) {
            tryToSaveImage();
        } else if (id == R.id.browse_images) {
            // TODO browsing images here
            Toast.makeText(this, "Browse images", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BrowseActivity.class);
            startActivity(intent);
        } else {
            throw new UnsupportedOperationException("Wrong menu item");
        }

        return super.onOptionsItemSelected(item);
    }

    private void tryToSaveImage() {
        try {
            saveImage();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.fileSaveFailureMessage), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage() throws IOException {
        Bitmap drawingBitmap = mDrawView.save();
        OutputStream imageOutputStream;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageOutputStream = receiveOutputStreamForNewerAPI();
        } else {
            imageOutputStream = receiveOutputStreamForOlderAPI();
        }

        drawingBitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOutputStream);
        imageOutputStream.close();
        Toast.makeText(this, getResources().getString(R.string.fileSavedSuccessMessage), Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private OutputStream receiveOutputStreamForNewerAPI() throws FileNotFoundException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, receiveFileName());
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        return getContentResolver().openOutputStream(uri);
    }

    @NonNull
    private OutputStream receiveOutputStreamForOlderAPI() throws FileNotFoundException {
        String imagesDirectory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File image = new File(imagesDirectory, receiveFileName());
        return new FileOutputStream(image);
    }

    private String receiveFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        String formattedDate = dateFormat.format(new Date());
        return String.format("img_%s.png", formattedDate);
    }
}