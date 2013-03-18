package TBianco.Drawing.First;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.*;



public class DrawingActivity extends Activity {
	
	private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        surfaceView = surfaceView = (SurfaceView)findViewById(R.id.drawingArea);
        surfaceHolder = surfaceView.getHolder();
        
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
    
    private void changeColor(int newColor){
    	color=newColor;
    	mPaint.setColor(color);
    }
    

}

