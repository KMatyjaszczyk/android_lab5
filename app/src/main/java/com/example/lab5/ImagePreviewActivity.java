package com.example.lab5;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ImagePreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, BrowseActivity.class);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_image_preview);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Image image = extras.getParcelable(BrowseActivity.CURRENT_IMAGE_KEY);
            FragmentDetails detailFragment = (FragmentDetails) getSupportFragmentManager()
                            .findFragmentById(R.id.detailFragment);
            detailFragment.setImage(image);
        }
    }
}