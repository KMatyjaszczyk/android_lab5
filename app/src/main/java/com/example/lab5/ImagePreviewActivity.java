package com.example.lab5;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ImagePreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.activity_image_preview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Image image = extras.getParcelable("currentImage");
            FragmentDetails detailFragment = (FragmentDetails)
                    getSupportFragmentManager()
                            .findFragmentById(R.id.detailFragment);
            detailFragment.setImage(image);
        }
    }
}