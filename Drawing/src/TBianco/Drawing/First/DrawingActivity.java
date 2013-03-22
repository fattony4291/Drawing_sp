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
import android.util.Log;
import android.view.*;
import android.widget.RadioButton;



public class DrawingActivity extends Activity {
	
	//private SurfaceView surfaceView;
    //private SurfaceHolder surfaceHolder;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        drawingArea = (MySurface)findViewById(R.id.drawingArea);
    }
    
    private MySurface drawingArea;
   
    private void changeColor(int newColor)
    {

    }
    
    public void undoClick(View view)
    {
    	drawingArea.undo();
    }
    
    public void redoClick(View view)
    {
    	drawingArea.redo();
    }
    
    public void deleteClick(View view)
    {
    	drawingArea.delete();    	
    }
    
    public void sizeChangeClick(View view)
    {
    	    boolean checked = ((RadioButton) view).isChecked();
    	    
    	    // Check which radio button was clicked
    	    switch(view.getId()) {
    	        case R.id.rSmall:
    	            if (checked)
    	                drawingArea.changeSize(6);
    	            break;
    	        case R.id.rMedium:
    	            if (checked)
    	            	drawingArea.changeSize(12);
    	            break;
    	        case R.id.rLarge:
    	            if (checked)
    	            	drawingArea.changeSize(18);
    	            break;
    	}	
    }
    
    public void modeChangeClick(View view)
    {
    	    boolean checked = ((RadioButton) view).isChecked();
    	    
    	    // Check which radio button was clicked
    	    switch(view.getId()) {
    	        case R.id.rDraw:
    	            if (checked)
    	                drawingArea.changeMode(Mode.DRAW_MODE);
    	            break;
    	        case R.id.rText:
    	            if (checked)
    	            	drawingArea.changeMode(Mode.TEXT_MODE);
    	            break;
    	        case R.id.rShapes:
    	            if (checked)
    	            	drawingArea.changeMode(Mode.SHAPE_MODE);
    	            break;
    	        case R.id.rErase:
    	            if (checked)
    	            	drawingArea.changeMode(Mode.ERASE_MODE);
    	            break;
    	        case R.id.rScroll:
    	            if (checked)
    	            	drawingArea.changeMode(Mode.SCROLL_MODE);
    	            break;
    	            
    	}	
    }
    
    public void homeClick(View view)
    {
    	drawingArea.resetTranslate();
    }
    

}

