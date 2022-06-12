package com.example.lab5;

import android.graphics.Path;

public class Stroke {
    private final int color;
    private final Path path;
    private final StrokePoint startPoint;
    private StrokePoint endPoint;

    public Stroke(int color, Path path, StrokePoint startPoint) {
        this.color = color;
        this.path = path;
        this.startPoint = startPoint;
    }

    public int getColor() {
        return color;
    }

    public Path getPath() {
        return path;
    }

    public StrokePoint getStartPoint() {
        return startPoint;
    }

    public StrokePoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(StrokePoint endPoint) {
        this.endPoint = endPoint;
    }
}
