package TBianco.Drawing.First;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import 	android.graphics.Canvas;

public class MySurface extends View {

    private Bitmap  mBitmap;
    private Canvas  mCanvas;
    private Path    mPath;
    private Paint   mBitmapPaint;
    private Paint 	mPaint;
    private List<Path> pathList;
    private int count;

    public MySurface(Context c) {
        super(c);

        init();
    }
    
    public MySurface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        
        init();
    }
    public MySurface(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        
        init();
    }
    
    private void init() {
    	mPath = new Path();
    	pathList = new ArrayList<Path>();
    	mCanvas = new Canvas();
        //mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        count = 0;
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //mCanvas = new Canvas(mBitmap);
        //mCanvas = new Canvas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFAAAAAA);

        //canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        
        for(Path x: pathList)
        {
        	canvas.drawPath(x, mPaint);
        	Log.d("pathloop"," ");
        }
        
        Log.d("pathlist.Length",pathList.toArray().length + "");

        canvas.drawPath(mPath, mPaint);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    private void touch_up() {
        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        //mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        //mPath.reset();
        pathList.add(new Path(mPath));
        Log.d("Drawing", "path added :" + count++); 
        
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

}

