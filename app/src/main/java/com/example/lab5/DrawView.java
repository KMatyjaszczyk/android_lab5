package com.example.lab5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DrawView extends View {
    private static final int DEFAULT_COLOR = Color.RED;
    private static final int STROKE_WIDTH = 10;
    private static final int CIRCLE_RADIUS = 15;
    private static final float TOUCH_TOLERANCE = 4;

    private final Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private final ArrayList<Line> lines = new ArrayList<>();

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Path mCurrentPath;
    private int mCurrentColor;
    private float mX;
    private float mY;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public void init(int height, int width) {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCurrentColor = DEFAULT_COLOR;
        mPaint = initPaint();
    }

    @NonNull
    private Paint initPaint() {
        final Paint mPaint;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mCurrentColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAlpha(0xff);
        return mPaint;
    }

    public void setColor(int color) {
        mCurrentColor = color;
    }

    public void clear() {
        lines.clear();
        drawBackground();
    }

    public Bitmap save() {
        return mBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        drawBackground();

        for (Line line : lines) {
            drawStroke(line);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void drawBackground() {
        int backgroundColor = Color.WHITE;
        mCanvas.drawColor(backgroundColor);
    }

    private void drawStroke(Line line) {
        mPaint.setColor(line.getColor());

        drawCircleOnStart(line);
        drawPath(line);
        drawCircleOnEnd(line);
    }

    private void drawCircleOnStart(Line line) {
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(line.getStartPoint().getX(), line.getStartPoint().getY(), CIRCLE_RADIUS, mPaint);
    }

    private void drawPath(Line line) {
        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawPath(line.getPath(), mPaint);
    }

    private void drawCircleOnEnd(Line line) {
        if (line.getEndPoint() != null) {
            mPaint.setStyle(Paint.Style.FILL);
            mCanvas.drawCircle(line.getEndPoint().getX(), line.getEndPoint().getY(), CIRCLE_RADIUS, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startDrawingStroke(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                proceedDrawingStroke(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                finishDrawingStroke();
                invalidate();
                break;
        }
        return true;
    }

    private void startDrawingStroke(float x, float y) {
        mCurrentPath = new Path();
        LinePoint startPoint = new LinePoint(x, y);
        Line line = new Line(mCurrentColor, mCurrentPath, startPoint);

        lines.add(line);
        mCurrentPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void proceedDrawingStroke(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        boolean moveIsSignificant = dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE;

        if (moveIsSignificant) {
            mCurrentPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void finishDrawingStroke() {
        mCurrentPath.lineTo(mX, mY);
        addEndPointToStroke();
    }

    private void addEndPointToStroke() {
        if (!lines.isEmpty()) {
            Line lastLine = lines.get(lines.size() - 1);

            lastLine.setEndPoint(new LinePoint(mX, mY));
        }
    }
}
