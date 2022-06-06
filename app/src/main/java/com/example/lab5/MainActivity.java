package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private DrawingSurface mDrawingSurface;
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
    }

    private void connectFieldsWithLayoutElements() {
        mDrawingSurface = findViewById(R.id.drawingSurface);
        mButtonColorRed = findViewById(R.id.buttonColorRed);
        mButtonColorOrange = findViewById(R.id.buttonColorOrange);
        mButtonColorBlue = findViewById(R.id.buttonColorGreen);
        mButtonClear = findViewById(R.id.buttonClear);
    }
}