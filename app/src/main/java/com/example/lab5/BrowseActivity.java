package com.example.lab5;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BrowseActivity extends AppCompatActivity implements
        FragmentList.OverviewFragmentActivityListener {
    public static final String CURRENT_IMAGE_KEY = "currentImage";
    public static final String SHOW_DETAILS_KEY = "showDetails";

    private boolean mShowDetails = false;
    private Image mCurrentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            setElementsFromIntents(intent);
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            processLandscapeOrientation();
        } else {
            processPortraitOrientation();
        }
    }

    private void setElementsFromIntents(Intent intent) {
        if (intent.hasExtra(CURRENT_IMAGE_KEY)) {
            mCurrentImage = intent.getExtras().getParcelable(CURRENT_IMAGE_KEY);
        }

        if (intent.hasExtra(SHOW_DETAILS_KEY)) {
            mShowDetails = intent.getExtras().getBoolean(SHOW_DETAILS_KEY);
        }
    }

    private void processLandscapeOrientation() {
        if (mShowDetails) {
            setContentView(R.layout.activity_browse_landscape);
            FragmentDetails detailFragment = (FragmentDetails) getSupportFragmentManager()
                            .findFragmentById(R.id.detailFragment);

            if (detailFragment != null && detailFragment.isInLayout()) {
                detailFragment.setImage(mCurrentImage);
            }
        }
        else {
            setContentView(R.layout.activity_browse);
        }
    }

    private void processPortraitOrientation() {
        if (mShowDetails) {
            Intent detailsIntent = new Intent(this, ImagePreviewActivity.class);
            detailsIntent.putExtra(CURRENT_IMAGE_KEY, mCurrentImage);
            finish();
            startActivity(detailsIntent);
        } else {
            setContentView(R.layout.activity_browse);
        }
    }


    @Override
    public void onItemSelected(String msg) {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
