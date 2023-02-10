package com.example.arkadiuszapp001;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Paint paint;

    public MyImageView (Context context,int  w,int h) {
        super(context);
        Log.d("xxx", " W:" + w +" H: "+ h);
        this.setBackgroundColor(0xff00ff00); // zielony kolor tła ImageView
        Resources r = getResources();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, h, r.getDisplayMetrics());
        this.setLayoutParams(new RelativeLayout.LayoutParams(w,height)); // wymiary ImageView
        //
        paint = new Paint(Paint.ANTI_ALIAS_FLAG); // obiekt Paint z atrybutami do rysowania
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL); // wypełnienie rysowanego na View elementu
        paint.setColor(0xffffff00);
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE); // krawędź rysowanego na View elementu
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(20, 20, 200, paint); // narysowanie okręgu w punkcie 20,20 i promieniu 200 (parametry względem punktu (0,0) w View w którym rysujemy)
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("xxx", "DOWN -> pozycja: " + event.getX() + "-" + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("xxx", "MOVE -> pozycja: " + event.getX() + "-" + event.getY());
                //odrysowanie canvasa
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.d("xxx", "UP -> pozycja: " + event.getX() + "-" + event.getY());
                break;
        }
        return true;
    }
    public void repaintAll(){
        invalidate();
    }
}