package TBianco.Drawing.First;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.*;



public class DrawingActivity extends Activity {
	
	//private SurfaceView surfaceView;
    //private SurfaceHolder surfaceHolder;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MySurface surfaceView = (MySurface)findViewById(R.id.drawingArea);
        
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        
        mPath= new Path();

        mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 },
                                       0.4f, 6, 3.5f);

        mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }

    private Paint       mPaint;
    private Path    	mPath;
    private MaskFilter  mEmboss;
    private MaskFilter  mBlur;
    private int color = 0xFFFF0000;
    
//    public class MyView extends View {
//
//        private static final float MINP = 0.25f;
//        private static final float MAXP = 0.75f;
//
//        private Bitmap  mBitmap;
//        private Canvas  mCanvas;
//        private Path    mPath;
//        private Paint   mBitmapPaint;
//
//        public MyView(Context c) {
//            super(c);
//
//            mPath = new Path();
//            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
//        }
//
//        @Override
//        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//            super.onSizeChanged(w, h, oldw, oldh);
//            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            mCanvas = new Canvas(mBitmap);
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//            canvas.drawColor(0xFFAAAAAA);
//
//            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
//
//            canvas.drawPath(mPath, mPaint);
//        }
//
//        private float mX, mY;
//        private static final float TOUCH_TOLERANCE = 4;
//
//        private void touch_start(float x, float y) {
//            mPath.reset();
//            mPath.moveTo(x, y);
//            mX = x;
//            mY = y;
//        }
//        private void touch_move(float x, float y) {
//            float dx = Math.abs(x - mX);
//            float dy = Math.abs(y - mY);
//            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
//                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
//                mX = x;
//                mY = y;
//            }
//        }
//        private void touch_up() {
//            mPath.lineTo(mX, mY);
//            // commit the path to our offscreen
//            mCanvas.drawPath(mPath, mPaint);
//            // kill this so we don't double draw
//            mPath.reset();
//        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent event) {
//            float x = event.getX();
//            float y = event.getY();
//
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    touch_start(x, y);
//                    invalidate();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    touch_move(x, y);
//                    invalidate();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    touch_up();
//                    invalidate();
//                    break;
//            }
//            return true;
//        }
//    }
    
    private void changeColor(int newColor){
    	color=newColor;
    	mPaint.setColor(color);
    }
    

}

