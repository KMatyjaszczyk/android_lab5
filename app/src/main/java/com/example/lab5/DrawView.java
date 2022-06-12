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
    private final ArrayList<Stroke> strokes = new ArrayList<>();

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
        strokes.clear();
        drawBackground();
    }

    public Bitmap save() {
        return mBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        drawBackground();

        for (Stroke stroke : strokes) {
            drawStroke(stroke);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void drawBackground() {
        int backgroundColor = Color.WHITE;
        mCanvas.drawColor(backgroundColor);
    }

    private void drawStroke(Stroke stroke) {
        mPaint.setColor(stroke.getColor());

        drawCircleOnStart(stroke);
        drawPath(stroke);
        drawCircleOnEnd(stroke);
    }

    private void drawCircleOnStart(Stroke stroke) {
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(stroke.getStartPoint().getX(), stroke.getStartPoint().getY(), CIRCLE_RADIUS, mPaint);
    }

    private void drawPath(Stroke stroke) {
        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawPath(stroke.getPath(), mPaint);
    }

    private void drawCircleOnEnd(Stroke stroke) {
        if (stroke.getEndPoint() != null) {
            mPaint.setStyle(Paint.Style.FILL);
            mCanvas.drawCircle(stroke.getEndPoint().getX(), stroke.getEndPoint().getY(), CIRCLE_RADIUS, mPaint);
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
        StrokePoint startPoint = new StrokePoint(x, y);
        Stroke stroke = new Stroke(mCurrentColor, mCurrentPath, startPoint);

        strokes.add(stroke);
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
        if (!strokes.isEmpty()) {
            Stroke lastStroke = strokes.get(strokes.size() - 1);

            lastStroke.setEndPoint(new StrokePoint(mX, mY));
        }
    }
}
