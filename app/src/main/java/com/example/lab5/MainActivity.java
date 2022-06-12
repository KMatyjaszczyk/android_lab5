package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;

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
}