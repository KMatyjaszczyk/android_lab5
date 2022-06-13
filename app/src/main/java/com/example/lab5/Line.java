package com.example.lab5;

import android.graphics.Path;

public class Line {
    private final int color;
    private final Path path;
    private final LinePoint startPoint;
    private LinePoint endPoint;

    public Line(int color, Path path, LinePoint startPoint) {
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

    public LinePoint getStartPoint() {
        return startPoint;
    }

    public LinePoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(LinePoint endPoint) {
        this.endPoint = endPoint;
    }
}
