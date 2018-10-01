package com.example.mich.myfirstapp;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RecAmplitudeGraph extends Drawable {
    private static final String TAG = "RecAmplitudeGraph";
    private final Paint mPaint;
    private final Paint mBackgroundColor;
    private final Paint mRedPaint;
    private int[] drawArray;
    private int maxValue = 1; // Это простой способ избежать деления на 0, поскольку это значение будет в знаменателе
    private int positionCounter = 0;


    public RecAmplitudeGraph() {
        mPaint = new Paint();
        mPaint.setARGB(255, 0, 0, 255);
        mBackgroundColor = new Paint();
        mBackgroundColor.setARGB(255, 255, 255, 255);
        mRedPaint = new Paint();
        mRedPaint.setARGB(255, 255, 0, 0);
    }

    /**
     * Draw in its bounds (set via setBounds) respecting optional effects such
     * as alpha (set via setAlpha) and color filter (set via setColorFilter).
     *
     * @param canvas The canvas to draw into
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
//        Log.d(TAG, "draw" + positionCounter);
        positionCounter++;
        // Get the drawable's bounds
        int width = getBounds().width();
        int height = getBounds().height();
        int numCells = drawArray.length;
        canvas.drawRect(0, 0, width, height, mBackgroundColor);


        for (int i = 0; i < numCells; i++) {
            // Draw a graph rectangle
            int value = drawArray[(i + positionCounter) % numCells];
            Paint drawPaint = mPaint;
            if (value == 0) {
                value = maxValue;
                drawPaint = mRedPaint;
            }
            int left = (width * i) / numCells;
            int top = height - (height * value) / maxValue;
            int right = (width * (i + 1)) / numCells;
//            int bottom = height;
            canvas.drawRect(left, top, right, height, drawPaint);
        }

    }

    /**
     * Specify an alpha value for the drawable. 0 means fully transparent, and
     * 255 means fully opaque.
     *
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {

    }

    /**
     * Specify an optional color filter for the drawable.
     *
     * @param colorFilter The color filter to apply, or {@code null} to remove the
     *                    existing color filter
     */
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    /**
     * @return int The opacity class of the Drawable.
     * @see PixelFormat
     */
    @Override
    public int getOpacity() {
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        return PixelFormat.OPAQUE;
    }

    public void setDrawArray(int[] drawArray) {
        this.drawArray = drawArray;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
