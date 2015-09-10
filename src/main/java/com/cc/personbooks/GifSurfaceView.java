package com.cc.personbooks;

/**
 * Created by Administrator on 2015-8-24.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

public class GifSurfaceView extends SurfaceView implements Callback {

    private SurfaceHolder holder;
    private Movie moive;
    private Handler mHandler = new Handler();

    private float zoom = 1f;

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            Canvas canvas = holder.lockCanvas();
            canvas.save();
            canvas.scale(zoom, zoom);
            moive.draw(canvas, 0, 0);

            moive.setTime((int) (System.currentTimeMillis() & moive.duration()));

            canvas.restore();
            // jiesuo
            holder.unlockCanvasAndPost(canvas);

            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, 0);
        }
    };

    public GifSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        mHandler.removeCallbacks(runnable);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // TODO Auto-generated method stub
        try {
            String fileName = "temp.gif";
            InputStream open = getContext().getAssets().open(fileName);
            if (open == null)
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            moive = Movie.decodeStream(open);


            int width = (int) (moive.width() * zoom);
            int height = (int) (moive.height() * zoom);
            setMeasuredDimension(width, height);

            mHandler.post(runnable);
            open.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void setZoom(float f) {
        // TODO Auto-generated method stub
        zoom = f;
    }


}
