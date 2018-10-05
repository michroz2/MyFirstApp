package com.example.mich.myfirstapp;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class VolumeTextComponent extends Drawable {
    private static final String TAG = "VolumeTextComponent";
    private final Paint mPaint;
    private final Paint mBackgroundPaint;
    private final Paint mRedPaint;
    private VolumeGraphComponent.ValueReceiver valueReceiver;

    VolumeTextComponent() {
        mPaint = new Paint();
        mPaint.setARGB(255, 0, 0, 255);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setARGB(255, 255, 255, 255);
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

        Log.d(TAG, "draw");

        // Get the drawable's bounds
        int width = getBounds().width();
        int height = getBounds().height();

        // Затираем предыдущую картинку
        canvas.drawRect(0, 0, width, height, mBackgroundPaint);

        // Получаем новое значение громкости:
        int newValue = valueReceiver.getValue();

        // Готовим формат текста
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setTextSize(height / 4 * 3);
        mPaint.setAntiAlias(true);
        //       mPaint.setTypeface(Typeface.createFromAsset(R.font.ssegbi));


        // выводим значение:
        canvas.drawText(String.valueOf(newValue), (width / 10 * 9), (height / 4 * 3), mPaint);

    }

    public void link(VolumeGraphComponent.ValueReceiver valueReceiver) {

        this.valueReceiver = valueReceiver;
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
}
