package TBianco.Drawing.First;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import 	android.graphics.Canvas;

public class MySurface extends SurfaceView implements
SurfaceHolder.Callback{

public MySurface(Context context) {
super(context);
// TODO Auto-generated constructor stub
}

@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width,
	int height) {
// TODO Auto-generated method stub

}

private Paint mPaint;

@Override
public void surfaceCreated(SurfaceHolder holder) {
// TODO Auto-generated method stub
	 mPaint = new Paint();
     mPaint.setAntiAlias(true);
     mPaint.setDither(true);
     mPaint.setColor(0xFFFF0000);
     mPaint.setStyle(Paint.Style.STROKE);
     mPaint.setStrokeJoin(Paint.Join.ROUND);
     mPaint.setStrokeCap(Paint.Cap.ROUND);
     mPaint.setStrokeWidth(12);

}

@Override
public void surfaceDestroyed(SurfaceHolder holder) {
// TODO Auto-generated method stub

}

private Path activePath = new Path();

public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();
    
    Canvas canvas = this.getHolder().lockCanvas();

    switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        	activePath.reset();
        	activePath.moveTo(x,y);
        	activePath.lineTo(x, y);
        	
        	//touch_start(x, y);
            invalidate();
            break;
        case MotionEvent.ACTION_MOVE:
            activePath.lineTo(x, y);
        	//touch_move(x, y);
            invalidate();
            break;
        case MotionEvent.ACTION_UP:
        	activePath.lineTo(x, y);
            //touch_up();
            invalidate();
            break;
    }
    canvas.drawPath(activePath,mPaint);
    
    this.getHolder().unlockCanvasAndPost(canvas);
    
    return true;
}

}

