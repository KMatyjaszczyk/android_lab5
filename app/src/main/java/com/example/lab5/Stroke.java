package com.example.lab5;

import android.graphics.Path;

public class Stroke {
    public int color;
    public int strokeWidth;
    public Path path;
    public StrokePoint startPoint;
    public StrokePoint endPoint;

    public Stroke(int color, int strokeWidth, Path path, StrokePoint startPoint) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
        this.startPoint = startPoint;
    }
}
